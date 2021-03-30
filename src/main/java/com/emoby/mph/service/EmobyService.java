package com.emoby.mph.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.emoby.mph.config.ApplicationProperties;
import com.emoby.mph.domain.Jobdescription;
import com.emoby.mph.domain.Sector;
import com.emoby.mph.domain.User;
import com.emoby.mph.service.dao.EmobyMatchingResponse;
import com.emoby.mph.service.dto.SectorDTO;
import com.emoby.mph.service.mapper.SectorMapper;

import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Jobdescription}.
 */
@Service
@Transactional
public class EmobyService {

    private final Logger log = LoggerFactory.getLogger(EmobyService.class);

    private final JobdescriptionService jobdescriptionService;

	private final UserService userService;

    private final SectorMapper sectorMapper;

	@Autowired
	private ApplicationProperties applicationProperties;
	
    public EmobyService(JobdescriptionService jobdescriptionService, UserService userService, SectorMapper sectorMapper) {
        this.jobdescriptionService = jobdescriptionService;
        this.userService = userService;
        this.sectorMapper = sectorMapper;
    }


    public EmobyMatchingResponse[] launchEmobyMatching(Long jobdescriptionId, List<SectorDTO> sectorsDTO) {
        log.debug("Request to launchEmobyMatching : {}", jobdescriptionId);
        
        List<Sector> sectors = null;
        sectors = sectorMapper.toEntity(sectorsDTO);
        
        if(sectors.size() == 0) {
        	Optional<User> user = userService.getUserWithAuthorities();
        	sectors.addAll(user.get().getSectors());
        }
        
		Mono<EmobyMatchingResponse[]> mono = callEmobyMatching(jobdescriptionId,sectors);
		Duration duration = Duration.of(applicationProperties.getEmobyMatchingTimeout(), ChronoUnit.SECONDS);

		return mono.block(duration);
    }
    
    //http://172.28.242.9:5010/api/v1/candidates/getMatching/1?sectors_authorized=1&sectors_authorized=2

	private Mono<EmobyMatchingResponse[]> callEmobyMatching(Long jobdescriptionId, List<Sector> sectors) {
	    log.debug("Starting callEmobyMatching");
	    
		Mono<EmobyMatchingResponse[]> emobyMatchingFlux = WebClient.create()
	      .get()
	      .uri(getEmobyMatchingUri(jobdescriptionId, sectors))
	      .retrieve()
	      .bodyToMono(EmobyMatchingResponse[].class)
	      .doOnError(error -> {
              throw new MatchingException(error.getMessage());
	      });
	 
	    return emobyMatchingFlux;
	}

	private URI getEmobyMatchingUri(Long jobdescriptionId, List<Sector> sectors) {
		URI result = null;
		try {
			result = new URI(applicationProperties.getEmobyMPHServiceURL()+"/getMatching/"+jobdescriptionId+displaySectors(sectors));
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
		return result;
	}
	
	private String displaySectors(List<Sector> sectors) {
		String result = "";

		for (int i = 0; i < sectors.size(); i++) {
			if(i==0) {
				result += "?";
			}
			
			Long id = sectors.get(i).getId();
			
			result += "sectors_authorized=";
			result += id;
			
			if(i+1 != sectors.size()) {
				result +="&";
			}
		}
		
		
		return result;
	}
}
