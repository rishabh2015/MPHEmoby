package com.emoby.mph.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.loader.tools.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import com.emoby.mph.config.ApplicationProperties;
import com.emoby.mph.domain.Content;
import com.emoby.mph.domain.Jobdescription;
import com.emoby.mph.repository.ContentRepository;
import com.emoby.mph.repository.JobdescriptionRepository;
import com.emoby.mph.service.dto.JobdescriptionCompactDTO;
import com.emoby.mph.service.dto.JobdescriptionDTO;
import com.emoby.mph.service.dto.TextCleanDTO;
import com.emoby.mph.service.mapper.JobdescriptionCompactMapper;
import com.emoby.mph.service.mapper.JobdescriptionMapper;
import com.google.common.io.Files;

import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Jobdescription}.
 */
@Service
@Transactional
public class JobdescriptionService extends TextCleanService {

    private final Logger log = LoggerFactory.getLogger(JobdescriptionService.class);

    private final JobdescriptionRepository jobdescriptionRepository;

    private final JobdescriptionMapper jobdescriptionMapper;

    private final JobdescriptionCompactMapper jobdescriptionCompactMapper;

	private final ContentRepository contentRepository;

	private final CandidateService candidateService;

	@Autowired
	private ApplicationProperties applicationProperties;
	
    public JobdescriptionService(JobdescriptionRepository jobdescriptionRepository, ContentRepository contentRepository, JobdescriptionMapper jobdescriptionMapper, JobdescriptionCompactMapper jobdescriptionCompactMapper, CandidateService candidateService, ApplicationProperties applicationProperties) {
        super(applicationProperties);
    	this.jobdescriptionRepository = jobdescriptionRepository;
        this.contentRepository = contentRepository;
        this.jobdescriptionMapper = jobdescriptionMapper;
        this.jobdescriptionCompactMapper = jobdescriptionCompactMapper;
        this.candidateService = candidateService;
    }

    /**
     * Save a jobdescription.
     *
     * @param jobdescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public JobdescriptionDTO save(JobdescriptionDTO jobdescriptionDTO) throws IOException {
        log.debug("Request to save Jobdescription : {}", jobdescriptionDTO);
        Jobdescription jobdescription = jobdescriptionMapper.toEntity(jobdescriptionDTO);
        
		File tempFile = null;
    	tempFile = File.createTempFile("abc", ".tmp", null);

        
		Mono<String> mono = callTextClean(jobdescriptionDTO.getContent().getData(), tempFile);
		Duration duration = Duration.of(applicationProperties.getTextCleanTimeout(), ChronoUnit.SECONDS);

		String textCleanDTO = mono.block(duration);
			
		if (textCleanDTO != null && textCleanDTO != "") {
			textCleanDTO = textCleanDTO.replaceAll("\"", "");
		} else {
			log.error("Text clean n'a pas répondu");
		}
		
		tempFile.delete();
        
		jobdescription.getContent().setText(textCleanDTO);
        Content content = contentRepository.save(jobdescription.getContent());
        jobdescription.setContent(content);
        
        jobdescription = jobdescriptionRepository.save(jobdescription);
        return jobdescriptionMapper.toDto(jobdescription);
    }

	/**
     * Get all the jobdescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<JobdescriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Jobdescriptions");
        return jobdescriptionRepository.findAll(pageable)
            .map(jobdescriptionMapper::toDto);
    }


    /**
     * Get all the jobdescriptions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<JobdescriptionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return jobdescriptionRepository.findAllWithEagerRelationships(pageable).map(jobdescriptionMapper::toDto);
    }

    /**
     * Get one jobdescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JobdescriptionDTO> findOne(Long id) {
        log.debug("Request to get Jobdescription : {}", id);
        return jobdescriptionRepository.findOneWithEagerRelationships(id)
            .map(jobdescriptionMapper::toDto);
    }

    /**
     * Delete the jobdescription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Jobdescription : {}", id);
        jobdescriptionRepository.deleteById(id);
    }

    /**
     * Get all the jobdescriptions with jhi_username
     * @param userId 
     *
     * @return the list of entities.
     */
    public Page<JobdescriptionCompactDTO> findAllJobDescriptionByUsername(Pageable pageable, Long userId) {
    	log.debug("Request to findAllJobDescriptionByUsername : {}");
    	Page<JobdescriptionCompactDTO> pages =  jobdescriptionRepository.findAllJobDescriptionByUsername(pageable, userId).map(jobdescriptionCompactMapper::toDto);
    	
    	for (JobdescriptionCompactDTO jobdescriptionCompactDTO : pages) {
    		int placedCount = candidateService.countPlacedByJobdescription(jobdescriptionCompactDTO.getId());
			int shortListedCount = candidateService.countShortlistedByJobdescription(jobdescriptionCompactDTO.getId());
			
			jobdescriptionCompactDTO.setPlacedCount(placedCount);
			jobdescriptionCompactDTO.setShortListedCount(shortListedCount);
		}
    	
    	return pages;
    }

    public boolean checkJobdescriptionAutorization(Long jobdescriptionId, Long userId) {
    	log.debug("Request to checkJobdescriptionAutorization : {}");
    	boolean authorized =  jobdescriptionRepository.checkJobdescriptionAutorization(jobdescriptionId, userId);
    	return authorized;
    }
}
