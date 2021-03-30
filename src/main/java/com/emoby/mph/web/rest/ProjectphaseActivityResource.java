package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.ProjectphaseActivityService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.ProjectphaseActivityDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emoby.mph.domain.ProjectphaseActivity}.
 */
@RestController
@RequestMapping("/api")
public class ProjectphaseActivityResource {

    private final Logger log = LoggerFactory.getLogger(ProjectphaseActivityResource.class);

    private static final String ENTITY_NAME = "projectphaseActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectphaseActivityService projectphaseActivityService;

    public ProjectphaseActivityResource(ProjectphaseActivityService projectphaseActivityService) {
        this.projectphaseActivityService = projectphaseActivityService;
    }

    /**
     * {@code POST  /projectphase-activities} : Create a new projectphaseActivity.
     *
     * @param projectphaseActivityDTO the projectphaseActivityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectphaseActivityDTO, or with status {@code 400 (Bad Request)} if the projectphaseActivity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projectphase-activities")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ProjectphaseActivityDTO> createProjectphaseActivity(@Valid @RequestBody ProjectphaseActivityDTO projectphaseActivityDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectphaseActivity : {}", projectphaseActivityDTO);
        if (projectphaseActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectphaseActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectphaseActivityDTO result = projectphaseActivityService.save(projectphaseActivityDTO);
        return ResponseEntity.created(new URI("/api/projectphase-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projectphase-activities} : Updates an existing projectphaseActivity.
     *
     * @param projectphaseActivityDTO the projectphaseActivityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectphaseActivityDTO,
     * or with status {@code 400 (Bad Request)} if the projectphaseActivityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectphaseActivityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projectphase-activities")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ProjectphaseActivityDTO> updateProjectphaseActivity(@Valid @RequestBody ProjectphaseActivityDTO projectphaseActivityDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectphaseActivity : {}", projectphaseActivityDTO);
        if (projectphaseActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectphaseActivityDTO result = projectphaseActivityService.save(projectphaseActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectphaseActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projectphase-activities} : get all the projectphaseActivities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectphaseActivities in body.
     */
    @GetMapping("/projectphase-activities")
    public List<ProjectphaseActivityDTO> getAllProjectphaseActivities() {
        log.debug("REST request to get all ProjectphaseActivities");
        return projectphaseActivityService.findAll();
    }

    /**
     * {@code GET  /projectphase-activities/:id} : get the "id" projectphaseActivity.
     *
     * @param id the id of the projectphaseActivityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectphaseActivityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projectphase-activities/{id}")
    public ResponseEntity<ProjectphaseActivityDTO> getProjectphaseActivity(@PathVariable Long id) {
        log.debug("REST request to get ProjectphaseActivity : {}", id);
        Optional<ProjectphaseActivityDTO> projectphaseActivityDTO = projectphaseActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectphaseActivityDTO);
    }

    /**
     * {@code DELETE  /projectphase-activities/:id} : delete the "id" projectphaseActivity.
     *
     * @param id the id of the projectphaseActivityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projectphase-activities/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteProjectphaseActivity(@PathVariable Long id) {
        log.debug("REST request to delete ProjectphaseActivity : {}", id);
        projectphaseActivityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
