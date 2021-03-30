package com.emoby.mph.service.impl;

import com.emoby.mph.config.ApplicationProperties;
import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.repository.JobOpeningRepository;
import com.emoby.mph.service.JobOpeningService;
import com.emoby.mph.service.TextCleanException;
import com.emoby.mph.service.TextCleanService;
import com.emoby.mph.service.dto.JobOpeningCompactDTO;
import com.emoby.mph.service.dto.JobOpeningDTO;
import com.emoby.mph.service.mapper.JobOpeningCompactMapper;
import com.emoby.mph.service.mapper.JobOpeningMapper;
import io.github.jhipster.web.util.HeaderUtil;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link JobOpening}.
 */
@Service
@Transactional
public class JobOpeningServiceImpl extends TextCleanService implements JobOpeningService {
    @Autowired
    private ApplicationProperties applicationProperties;

    private final Logger log = LoggerFactory.getLogger(JobOpeningServiceImpl.class);

    private final JobOpeningRepository jobOpeningRepository;

    private final JobOpeningMapper jobOpeningMapper;

    private final JobOpeningCompactMapper jobOpeningCompactMapper;

    public JobOpeningServiceImpl(
        JobOpeningRepository jobOpeningRepository,
        JobOpeningMapper jobOpeningMapper,
        JobOpeningCompactMapper jobOpeningCompactMapper,
        ApplicationProperties applicationProperties
    ) {
        super(applicationProperties);
        this.jobOpeningRepository = jobOpeningRepository;
        this.jobOpeningMapper = jobOpeningMapper;
        this.jobOpeningCompactMapper = jobOpeningCompactMapper;
    }

    @Override
    public JobOpeningDTO save(JobOpeningDTO jobOpeningDTO) {
        log.debug("Request to save JobOpening : {}", jobOpeningDTO);
        JobOpening jobOpening = jobOpeningMapper.toEntity(jobOpeningDTO);
        jobOpening = jobOpeningRepository.save(jobOpening);
        return jobOpeningMapper.toDto(jobOpening);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobOpeningDTO> findAll() {
        log.debug("Request to get all JobOpenings");
        return jobOpeningRepository.findAll().stream().map(jobOpeningMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobOpeningDTO> findOne(Long id) {
        log.debug("Request to get JobOpening : {}", id);
        return jobOpeningRepository.findById(id).map(jobOpeningMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobOpening : {}", id);
        jobOpeningRepository.deleteById(id);
    }

    @Override
    public Long findIdJobOpeningByUUID(UUID uuid) {
        return jobOpeningRepository.findByUUID(uuid);
    }

    @Override
    public List<JobOpeningCompactDTO> getJobOpeningByTitle(String title) {
        Pageable limit = PageRequest.of(0, 200);
        title = StringUtils.lowerCase(title);
        Page<JobOpening> result = jobOpeningRepository.findJobOpeningByTitle(limit, title);
        for (JobOpening jobOpening : result) {
            jobOpening.setTitle(jobOpening.getTitle().substring(0, 1).toUpperCase() + jobOpening.getTitle().substring(1));
        }

        return result.stream().map(jobOpeningCompactMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public JobOpeningCompactDTO createOrUpdateJobOpening(JobOpeningCompactDTO jobOpeningCompactDTO) throws IOException {
        Long id = findIdJobOpeningByUUID(jobOpeningCompactDTO.getGuid());

        JobOpeningDTO jobOpeningDTO = new JobOpeningDTO();
        jobOpeningDTO.setId(id);
        jobOpeningDTO.setGuid(jobOpeningCompactDTO.getGuid());
        jobOpeningDTO.setCreation_date(Instant.now());
        jobOpeningDTO.setDelete_date(jobOpeningCompactDTO.getDelete_date());
        jobOpeningDTO.setJobdescription_text(jobOpeningCompactDTO.getJobdescription_text());
        jobOpeningDTO.setTitle(jobOpeningCompactDTO.getTitle());

        if(jobOpeningCompactDTO.getFile() != null) {
            File tempFile = null;
            tempFile = File.createTempFile("abc", ".tmp", null);

            Mono<String> mono = callTextClean(jobOpeningCompactDTO.getFile(), tempFile);
            Duration duration = Duration.of(applicationProperties.getTextCleanTimeout(), ChronoUnit.SECONDS);

            String textCleanDTO = mono.block(duration);

            tempFile.delete();

            if (textCleanDTO != null && textCleanDTO != "") {
                textCleanDTO = textCleanDTO.replaceAll("\"", "");
                jobOpeningDTO.setText_clean(textCleanDTO);
            } else {
                log.error("Text clean n'a pas répondu");
                throw new TextCleanException();
            }
        }
        


        JobOpeningCompactDTO result = save(jobOpeningDTO);

        return result;
    }
}
