package com.emoby.mph.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.JobdescriptionService;
import com.emoby.mph.service.dto.JobdescriptionCompactDTO;
import com.emoby.mph.service.dto.JobdescriptionDTO;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.emoby.mph.domain.Jobdescription}.
 */
@RestController
@RequestMapping("/api")
public class JobdescriptionResource {

    private final Logger log = LoggerFactory.getLogger(JobdescriptionResource.class);

    private static final String ENTITY_NAME = "jobdescription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobdescriptionService jobdescriptionService;

    public JobdescriptionResource(JobdescriptionService jobdescriptionService) {
        this.jobdescriptionService = jobdescriptionService;
    }

    /**
     * {@code POST  /jobdescriptions} : Create a new jobdescription.
     *
     * @param jobdescriptionDTO the jobdescriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobdescriptionDTO, or with status {@code 400 (Bad Request)} if the jobdescription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException 
     */
    @PostMapping("/jobdescriptions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\") ||  hasAuthority(\""+ AuthoritiesConstants.USER+ "\")")
    public ResponseEntity<JobdescriptionDTO> createJobdescription(@Valid @RequestBody JobdescriptionDTO jobdescriptionDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Jobdescription : {}", jobdescriptionDTO);
        if (jobdescriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobdescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobdescriptionDTO result = jobdescriptionService.save(jobdescriptionDTO);
        return ResponseEntity.created(new URI("/api/jobdescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /jobdescriptions} : Updates an existing jobdescription.
     *
     * @param jobdescriptionDTO the jobdescriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobdescriptionDTO,
     * or with status {@code 400 (Bad Request)} if the jobdescriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobdescriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException 
     */
    @PutMapping("/jobdescriptions")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\") ||  hasAuthority(\""+ AuthoritiesConstants.USER+ "\")")
    public ResponseEntity<JobdescriptionDTO> updateJobdescription(@Valid @RequestBody JobdescriptionDTO jobdescriptionDTO) throws URISyntaxException, IOException {
        log.debug("REST request to update Jobdescription : {}", jobdescriptionDTO);
        if (jobdescriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobdescriptionDTO result = jobdescriptionService.save(jobdescriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobdescriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /jobdescriptions} : get all the jobdescriptions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobdescriptions in body.
     */
    @GetMapping("/jobdescriptions")
    public ResponseEntity<List<JobdescriptionDTO>> getAllJobdescriptions(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Jobdescriptions");
        Page<JobdescriptionDTO> page;
        if (eagerload) {
            page = jobdescriptionService.findAllWithEagerRelationships(pageable);
        } else {
            page = jobdescriptionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jobdescriptions/:id} : get the "id" jobdescription.
     *
     * @param id the id of the jobdescriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobdescriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/jobdescriptions/{id}")
    public ResponseEntity<JobdescriptionDTO> getJobdescription(@PathVariable Long id) {
        log.debug("REST request to get Jobdescription : {}", id);
        Optional<JobdescriptionDTO> jobdescriptionDTO = jobdescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobdescriptionDTO);
    }

    /**
     * {@code DELETE  /jobdescriptions/:id} : delete the "id" jobdescription.
     *
     * @param id the id of the jobdescriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/jobdescriptions/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteJobdescription(@PathVariable Long id) {
        log.debug("REST request to delete Jobdescription : {}", id);
        jobdescriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * {@code GET  /findAllJobDescriptionByUsername} : get all the jobdescriptionsbyusername.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobdescriptions in body.
     */
	@GetMapping("/findAllJobDescriptionByUsername")
	public ResponseEntity<List<JobdescriptionCompactDTO>> findAllJobDescriptionByUsername(Pageable pageable, @RequestParam(value = "loginId") Long loginId) {
		log.debug("REST request to get a page of findAllJobDescriptionByUsername");
		Page<JobdescriptionCompactDTO> page;
		page = jobdescriptionService.findAllJobDescriptionByUsername(pageable,loginId);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
