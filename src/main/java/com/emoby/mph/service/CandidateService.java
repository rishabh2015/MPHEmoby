package com.emoby.mph.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.emoby.mph.config.ApplicationProperties;
import com.emoby.mph.domain.Candidate;
import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.repository.CandidateRepository;
import com.emoby.mph.repository.JobOpeningRepository;
import com.emoby.mph.service.dao.EmobyMatchingResponse;
import com.emoby.mph.service.dao.EmobySearchCandidateMatchingRequest;
import com.emoby.mph.service.dao.EmobySearchCandidateMatchingResponse;
import com.emoby.mph.service.dao.MPHContentResponse;
import com.emoby.mph.service.dao.MPHResponse;
import com.emoby.mph.service.dto.CandidateDTO;
import com.emoby.mph.service.dto.SearchCandidatesRequestDTO;
import com.emoby.mph.service.dto.SearchCandidatesResponseDTO;
import com.emoby.mph.service.mapper.CandidateMapper;
import com.emoby.mph.service.mapper.CandidateSearchRequestMapper;
import com.emoby.mph.service.mapper.CandidateSearchResponseMapper;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.ProxyProvider;

/**
 * Service Implementation for managing {@link Candidate}.
 */
@Service
@Transactional
public class CandidateService {

	private final Logger log = LoggerFactory.getLogger(CandidateService.class);

	private ApplicationProperties applicationProperties;

	private final CandidateRepository candidateRepository;

	private final JobOpeningRepository jobOpeningRepository;

	private final CandidateMapper candidateMapper;

	private final CandidateSearchRequestMapper candidateSearchRequestMapper;

	private final CandidateSearchResponseMapper candidateSearchResponseMapper;

	private final WebClient webClientForEmobyPython;

	
	public CandidateService(ApplicationProperties applicationProperties, CandidateRepository candidateRepository, CandidateMapper candidateMapper, JobOpeningRepository jobOpeningRepository, CandidateSearchRequestMapper candidateSearchRequestMapper, CandidateSearchResponseMapper candidateSearchResponseMapper) {
		this.candidateRepository = candidateRepository;
		this.candidateMapper = candidateMapper;
		this.jobOpeningRepository = jobOpeningRepository;
		this.candidateSearchRequestMapper = candidateSearchRequestMapper;
		this.candidateSearchResponseMapper = candidateSearchResponseMapper;
		this.applicationProperties = applicationProperties;
    	this.webClientForEmobyPython = WebClient.builder().baseUrl(applicationProperties.getEmobyMPHServiceURL()).build();
	}

	/**
	 * Save a candidate.
	 *
	 * @param candidateDTO the entity to save.
	 * @return the persisted entity.
	 */
	public CandidateDTO save(CandidateDTO candidateDTO) {
		log.debug("Request to save Candidate : {}", candidateDTO);
		Candidate candidate = candidateMapper.toEntity(candidateDTO);
		candidate = candidateRepository.save(candidate);
		return candidateMapper.toDto(candidate);
	}

	/**
	 * Get all the candidates.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public Page<CandidateDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Candidates");
		return candidateRepository.findAll(pageable).map(candidateMapper::toDto);
	}

	/**
	 * Get all the candidates with eager load of many-to-many relationships.
	 *
	 * @return the list of entities.
	 */
	public Page<CandidateDTO> findAllWithEagerRelationships(Pageable pageable) {
		return candidateRepository.findAllWithEagerRelationships(pageable).map(candidateMapper::toDto);
	}

	/**
	 * Get one candidate by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<CandidateDTO> findOne(Long id) {
		log.debug("Request to get Candidate : {}", id);
		Optional<Candidate> candidate = candidateRepository.findOneWithEagerRelationships(id);
		return candidate.map(candidateMapper::toDto);
	}

	/**
	 * Delete the candidate by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete Candidate : {}", id);
		candidateRepository.deleteById(id);
	}

	/**
	 * Count shortListed by jobdescriptionId.
	 *
	 * @param jobdescriptionId the id of the jobdescription.
	 * @return the count of candidate with candidate.shortListed=jobdescription.id.
	 */
	public int countShortlistedByJobdescription(Long jobdescriptionId) {
		log.debug("Request to countShortlistedByJobdescription : {}", jobdescriptionId);
		return candidateRepository.countShortlistedByJobdescription(jobdescriptionId);
	}

	/**
	 * Count placed by jobdescriptionId.
	 *
	 * @param jobdescriptionId the id of the jobdescription.
	 * @return the count of candidate with candidate.placed=jobdescription.id.
	 */
	public int countPlacedByJobdescription(Long jobdescriptionId) {
		log.debug("Request to countPlacedByJobdescription : {}", jobdescriptionId);
		return candidateRepository.countPlacedByJobdescription(jobdescriptionId);
	}

	@Transactional(readOnly = false)
	public int setShortlisted(Long candidateId, Long jobdescriptionId) {
		log.debug("Request to setShortlisted : {0} {1}", candidateId, jobdescriptionId);
		// Modification base de données postgresSQL
		int i = candidateRepository.setShortlisted(candidateId, jobdescriptionId);

		if (i != 0) {
			Optional<Candidate> candidate = candidateRepository.findById(candidateId);

			if (!candidate.isPresent()) {
				throw new NoCandidateException();
			} else {
				// Modification Service MPH
				MPHResponse response;
				if (jobdescriptionId != null) {
					log.debug("***Set shortlisted dans Dynamics " + candidate.get().getGuid().toString());
					response = callPostMphDynamicsServiceURL(getMphDynamicsSetShortlistedURL(),
							candidate.get().getGuid().toString());
				} else {
					log.debug("***Set active dans Dynamics " + candidate.get().getGuid().toString());
					response = callPostMphDynamicsServiceURL(getMphDynamicsSetActiveURL(),
							candidate.get().getGuid().toString());
				}
				if (response == null || response.getCode() != 200) {
					throw new MphDynamicsException(
							"Erreur MPH Dynamics setShortlisted : " + response.getCode() + " " + jobdescriptionId);
				} else {
					log.debug("***Set OK");
				}
			}
		}

		return i;
	}

	@Transactional(readOnly = false)
	public int setPlaced(Long candidateId, Long jobdescriptionId) {
		log.debug("Request to setPlaced : {0} {1}", candidateId, jobdescriptionId);
		// Modification base de données postgresSQL
		int i = candidateRepository.setPlaced(candidateId, jobdescriptionId);

		if (i != 0) {
			Optional<Candidate> candidate = candidateRepository.findById(candidateId);

			if (!candidate.isPresent()) {
				throw new NoCandidateException();
			} else {
				// Modification Service MPH
				MPHResponse response;
				if (jobdescriptionId != null) {
					log.debug("***Set placed dans Dynamics " + candidate.get().getGuid().toString());
					response = callPostMphDynamicsServiceURL(getMphDynamicsSetPlacedURL(),
							candidate.get().getGuid().toString());
				} else {
					log.debug("***Set shortlisted dans Dynamics " + candidate.get().getGuid().toString());
					response = callPostMphDynamicsServiceURL(getMphDynamicsSetShortlistedURL(),
							candidate.get().getGuid().toString());
				}
				if (response == null || response.getCode() != 200) {
					throw new MphDynamicsException(
							"Erreur MPH Dynamics setPlaced : " + response.getCode() + " " + jobdescriptionId);
				} else {
					log.debug("***Set OK");
				}
			}
		}

		return i;
	}

	public MPHResponse getCvByIdDocument(Long candidateId) {
		Optional<Candidate> candidate = candidateRepository.findById(candidateId);

		if (!candidate.isPresent()) {
			throw new NoCandidateException();
		} else {
			MPHResponse response = callGetMphDynamicsServiceURL(
					getMphDynamicsGetCVByIdURL(candidate.get().getGuid().toString()),
					candidate.get().getGuid().toString());
			return response;
		}
	}

	/*
	 * Methode pour appeler Dynamics en GET
	 */
	private MPHResponse callGetMphDynamicsServiceURL(URI uri, String guid) {
		log.debug("Starting callGetMphDynamicsServiceURL");

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("Id", guid);

		MPHResponse responseJson = getWebclient().get().uri(uri).retrieve().bodyToMono(MPHResponse.class).block();

		return responseJson;
	}

	/*
	 * Methode pour appeler Dynamics en Post
	 */
	private MPHResponse callPostMphDynamicsServiceURL(URI uri, String guid) {
		log.debug("Starting callPostMphDynamicsServiceURL");

		Map<String, String> requestBody = new HashMap<String, String>();
		requestBody.put("Id", guid);
		
		MPHResponse responseJson = getWebclient().post().uri(uri).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(requestBody)).retrieve().bodyToMono(MPHResponse.class).block();

		return responseJson;
	}
	
	/*
	 * Methode pour appeler Search Candidate Matching en POST
	 */
	private EmobySearchCandidateMatchingResponse[] callPostSearchCandidateMatchingURL(URI uri, EmobySearchCandidateMatchingRequest emobySearchCandidateMatchingRequest) {
		log.debug("Starting callPostSearchCandidateMatchingURL");
		
		EmobySearchCandidateMatchingResponse[] responseJson = webClientForEmobyPython.post().uri("/{url}","search")
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(emobySearchCandidateMatchingRequest), EmobySearchCandidateMatchingRequest.class).retrieve().bodyToMono(EmobySearchCandidateMatchingResponse[].class).block();
		
		
		return responseJson;
	}
	
	private WebClient getWebclient() {
		WebClient client;
		if(StringUtils.isNotEmpty(applicationProperties.getProxyHost()) && applicationProperties.getProxyPort() != null) 
		{
			HttpClient httpClient = HttpClient.create().tcpConfiguration(tcpClient -> tcpClient
					.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).host(applicationProperties.getProxyHost()).port(applicationProperties.getProxyPort())));
			ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
			client = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
		            .codecs(configurer -> configurer
		                      .defaultCodecs()
		                      .maxInMemorySize(20 * 1024 * 1024))
		                    .build()).clientConnector(connector).build();
		
		} else {
			client = WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
		            .codecs(configurer -> configurer
		                      .defaultCodecs()
		                      .maxInMemorySize(20 * 1024 * 1024))
		                    .build())
		                  .build();
		}
		
		return client;
	}

	private URI getMphDynamicsSetShortlistedURL() {
		URI result = null;
		try {
			result = new URI(applicationProperties.getMphDynamicsServiceURL() + "/setShortlistedCandidate");
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
		return result;
	}

	private URI getMphDynamicsSetPlacedURL() {
		URI result = null;
		try {
			result = new URI(applicationProperties.getMphDynamicsServiceURL() + "/setPlacedCandidate");
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
		return result;
	}

	private URI getMphDynamicsSetActiveURL() {
		URI result = null;
		try {
			result = new URI(applicationProperties.getMphDynamicsServiceURL() + "/setActiveCandidate");
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
		return result;
	}
	
	private URI getSearchCandidateMatchingURL() {
		URI result = null;
		try {
			result = new URI(applicationProperties.getEmobyMPHServiceURL() + "/searchCandidate");
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
		return result;
	}

	private URI getMphDynamicsGetCVByIdURL(String guid) {
		URI result = null;
		try {
			result = new URI(
					applicationProperties.getMphDynamicsServiceURL() + "/getCvByIdDocument?CandidateId=" + guid);
		} catch (URISyntaxException e) {
			log.error(e.getMessage(), e.getCause());
			e.printStackTrace();
		}
		return result;
	}

	public void setCandidateComment(Long candidateId, String comment) {
		Optional<Candidate> candidate = candidateRepository.findById(candidateId);
		if (!candidate.isPresent()) {
			throw new NoCandidateException();
		} else {
			candidate.get().setComment(comment);
			candidateRepository.save(candidate.get());
		}
	}

	public List<SearchCandidatesResponseDTO> searchCandidates(@Valid SearchCandidatesRequestDTO searchCandidatesRequestDTO) {
		List<SearchCandidatesResponseDTO> result = new ArrayList<SearchCandidatesResponseDTO>();
		
		
		Optional<JobOpening> jobOpening = Optional.empty();
		if(searchCandidatesRequestDTO.getJobOpeningId() != null) {
			jobOpening = jobOpeningRepository.findById(searchCandidatesRequestDTO.getJobOpeningId());
		}
		EmobySearchCandidateMatchingRequest emobySearchCandidateMatchingRequest = candidateSearchRequestMapper.toEntity(searchCandidatesRequestDTO);
		
		
		
		if(jobOpening.isPresent()) {
			JobOpening jo = jobOpening.get();
			if(jo.getJobdescription_text() == null) {
				jo.setJobdescription_text("");
			}
			if(jo.getText_clean() == null) {
				jo.setText_clean("");
			}
			emobySearchCandidateMatchingRequest.setJobOpening(jobOpening.get());
		}
		
		EmobySearchCandidateMatchingResponse[] response = callPostSearchCandidateMatchingURL(getSearchCandidateMatchingURL(), emobySearchCandidateMatchingRequest);
		for (EmobySearchCandidateMatchingResponse emobySearchCandidateMatchingResponse : response) {
			float f = ((float) ((int) (emobySearchCandidateMatchingResponse.getMatching_percent()*10000)))/100;
			emobySearchCandidateMatchingResponse.setMatching_percent((float) Math.round(f));
			if(jobOpening.isPresent()) {
				emobySearchCandidateMatchingResponse.setJob_opening_id(jobOpening.get().getId());
				emobySearchCandidateMatchingResponse.setJob_opening_title(jobOpening.get().getTitle());
			}
			SearchCandidatesResponseDTO dto = candidateSearchResponseMapper.toDto(emobySearchCandidateMatchingResponse);
			dto.setName(emobySearchCandidateMatchingResponse.getFirst_name()+ " "+emobySearchCandidateMatchingResponse.getLast_name());
			result.add(dto);
		}
		
		return result;
	}

}
