package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.ProjectphaseService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.ProjectphaseDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.Projectphase}.
 */
@RestController
@RequestMapping("/api")
public class ProjectphaseResource {

    private final Logger log = LoggerFactory.getLogger(ProjectphaseResource.class);

    private static final String ENTITY_NAME = "projectphase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectphaseService projectphaseService;

    public ProjectphaseResource(ProjectphaseService projectphaseService) {
        this.projectphaseService = projectphaseService;
    }

    /**
     * {@code POST  /projectphases} : Create a new projectphase.
     *
     * @param projectphaseDTO the projectphaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectphaseDTO, or with status {@code 400 (Bad Request)} if the projectphase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projectphases")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ProjectphaseDTO> createProjectphase(@Valid @RequestBody ProjectphaseDTO projectphaseDTO) throws URISyntaxException {
        log.debug("REST request to save Projectphase : {}", projectphaseDTO);
        if (projectphaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectphase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectphaseDTO result = projectphaseService.save(projectphaseDTO);
        return ResponseEntity.created(new URI("/api/projectphases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projectphases} : Updates an existing projectphase.
     *
     * @param projectphaseDTO the projectphaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectphaseDTO,
     * or with status {@code 400 (Bad Request)} if the projectphaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectphaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projectphases")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ProjectphaseDTO> updateProjectphase(@Valid @RequestBody ProjectphaseDTO projectphaseDTO) throws URISyntaxException {
        log.debug("REST request to update Projectphase : {}", projectphaseDTO);
        if (projectphaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProjectphaseDTO result = projectphaseService.save(projectphaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectphaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projectphases} : get all the projectphases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectphases in body.
     */
    @GetMapping("/projectphases")
    public List<ProjectphaseDTO> getAllProjectphases() {
        log.debug("REST request to get all Projectphases");
        return projectphaseService.findAll();
    }

    /**
     * {@code GET  /projectphases/:id} : get the "id" projectphase.
     *
     * @param id the id of the projectphaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectphaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projectphases/{id}")
    public ResponseEntity<ProjectphaseDTO> getProjectphase(@PathVariable Long id) {
        log.debug("REST request to get Projectphase : {}", id);
        Optional<ProjectphaseDTO> projectphaseDTO = projectphaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectphaseDTO);
    }

    /**
     * {@code DELETE  /projectphases/:id} : delete the "id" projectphase.
     *
     * @param id the id of the projectphaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projectphases/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteProjectphase(@PathVariable Long id) {
        log.debug("REST request to delete Projectphase : {}", id);
        projectphaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
