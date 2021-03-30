package com.emoby.mph.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emoby.mph.config.ApplicationProperties;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.JobOpeningService;
import com.emoby.mph.service.dto.JobOpeningCompactDTO;
import com.emoby.mph.service.dto.JobOpeningDTO;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emoby.mph.domain.JobOpening}.
 */
@RestController
@RequestMapping("/api")
public class JobOpeningResource {

    private final Logger log = LoggerFactory.getLogger(JobOpeningResource.class);

	@Autowired
	private ApplicationProperties applicationProperties;
    
    private static final String ENTITY_NAME = "jobOpening";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobOpeningService jobOpeningService;

    public JobOpeningResource(JobOpeningService jobOpeningService) {
        this.jobOpeningService = jobOpeningService;
    }

	/**
	 * {@code POST  /job-openings} : Create a new jobOpening.
	 *
	 * @param jobOpeningCompactDTO the jobOpeningDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new jobOpeningDTO, or with status {@code 400 (Bad Request)}
	 *         if the jobOpening has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	@PostMapping("/createOrUpdateJobOpening")
	public ResponseEntity<Long> createOrUpdateJobOpening(@Valid @RequestBody JobOpeningCompactDTO jobOpeningCompactDTO)
			throws URISyntaxException, InterruptedException, IOException {
		log.debug("REST request to save JobOpening : {}", jobOpeningCompactDTO);
		
		JobOpeningCompactDTO result = jobOpeningService.createOrUpdateJobOpening(jobOpeningCompactDTO);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString())).body(result.getId());
	}
    
    /**
     * {@code POST  /job-openings} : Create a new jobOpening.
     *
     * @param jobOpeningDTO the jobOpeningDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobOpeningDTO, or with status {@code 400 (Bad Request)} if the jobOpening has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-openings")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<JobOpeningDTO> createJobOpening(@Valid @RequestBody JobOpeningDTO jobOpeningDTO) throws URISyntaxException {
        log.debug("REST request to save JobOpening : {}", jobOpeningDTO);
        if (jobOpeningDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobOpening cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobOpeningDTO result = jobOpeningService.save(jobOpeningDTO);
        return ResponseEntity.created(new URI("/api/job-openings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-openings} : Updates an existing jobOpening.
     *
     * @param jobOpeningDTO the jobOpeningDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobOpeningDTO,
     * or with status {@code 400 (Bad Request)} if the jobOpeningDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobOpeningDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-openings")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<JobOpeningDTO> updateJobOpening(@Valid @RequestBody JobOpeningDTO jobOpeningDTO) throws URISyntaxException {
        log.debug("REST request to update JobOpening : {}", jobOpeningDTO);
        if (jobOpeningDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobOpeningDTO result = jobOpeningService.save(jobOpeningDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobOpeningDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /job-openings} : get all the jobOpenings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobOpenings in body.
     */
    @GetMapping("/job-openings")
    public List<JobOpeningDTO> getAllJobOpenings() {
        log.debug("REST request to get all JobOpenings");
        return jobOpeningService.findAll();
    }

    /**
     * {@code GET  /job-openings/:id} : get the "id" jobOpening.
     *
     * @param id the id of the jobOpeningDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobOpeningDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-openings/{id}")
    public ResponseEntity<JobOpeningDTO> getJobOpening(@PathVariable Long id) {
        log.debug("REST request to get JobOpening : {}", id);
        Optional<JobOpeningDTO> jobOpeningDTO = jobOpeningService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobOpeningDTO);
    }
    
    @GetMapping("/jobopening")
    public List<JobOpeningCompactDTO> getJobOpeningByTitle(@RequestParam(required = false, defaultValue = "") String title) {
        log.info("REST request to getJobOpeningByTitle : " + title);
        List<JobOpeningCompactDTO> result = jobOpeningService.getJobOpeningByTitle(title);
        log.info("Fin getJobOpeningByTitle => " + result.size());
		return result;
    }

    /**
     * {@code DELETE  /job-openings/:id} : delete the "id" jobOpening.
     *
     * @param id the id of the jobOpeningDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-openings/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteJobOpening(@PathVariable Long id) {
        log.debug("REST request to delete JobOpening : {}", id);
        jobOpeningService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
