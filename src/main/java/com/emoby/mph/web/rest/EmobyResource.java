package com.emoby.mph.web.rest;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emoby.mph.domain.Candidate;
import com.emoby.mph.domain.Jobdescription;
import com.emoby.mph.domain.PotentialCandidate;
import com.emoby.mph.repository.PotentialCandidateRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.EmobyService;
import com.emoby.mph.service.dao.EmobyMatchingResponse;
import com.emoby.mph.service.dto.LaunchEmobyMatchingDTO;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emoby.mph.domain.Activity}.
 */
@RestController
@RequestMapping("/api")
public class EmobyResource {

    private final Logger log = LoggerFactory.getLogger(EmobyResource.class);
	
    private final EmobyService emobyService;

    private PotentialCandidateRepository potentialCandidateRepository;
    
    public EmobyResource(EmobyService emobyService, PotentialCandidateRepository potentialCandidateRepository) {
        this.emobyService = emobyService;
        this.potentialCandidateRepository = potentialCandidateRepository;
    }
	
    /**
     * launchEmoby. (call DataScientist service)
     *
     * @param jobdescriptionId the jobdescriptionId to call.
     * @return void.
     */
    @PostMapping("/launchEmobyMatching")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\") ||  hasAuthority(\""+ AuthoritiesConstants.USER+ "\")")
    public ResponseEntity<Long> launchEmobyMatching(@RequestBody LaunchEmobyMatchingDTO launchEmobyMatchingDTO) throws URISyntaxException, InterruptedException {
        log.debug("REST request to launchEmobyMatching : {}", launchEmobyMatchingDTO);
        
        EmobyMatchingResponse[] result = emobyService.launchEmobyMatching(launchEmobyMatchingDTO.getJobdescriptionId(), launchEmobyMatchingDTO.getSectors());
    	
        List<PotentialCandidate> entities = new ArrayList<PotentialCandidate>();
        
        for (EmobyMatchingResponse emobyMatchingResponse : result) {
        	PotentialCandidate pc = new PotentialCandidate();
        	pc.setCandidate(new Candidate(emobyMatchingResponse.getId()));
			pc.setJobdescription(new Jobdescription(launchEmobyMatchingDTO.getJobdescriptionId()));
			pc.setCreation_dt(Instant.now());
			float f = ((float) ((int) (emobyMatchingResponse.getSim()*10000)))/100;
			pc.setMatching_percent(f);
			entities.add(pc);
		}
        
		potentialCandidateRepository.saveAll(entities);
        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(launchEmobyMatchingDTO.getJobdescriptionId()));
    }

}
