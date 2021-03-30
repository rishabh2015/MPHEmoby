package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.SubsectorService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.SubsectorDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.Subsector}.
 */
@RestController
@RequestMapping("/api")
public class SubsectorResource {

    private final Logger log = LoggerFactory.getLogger(SubsectorResource.class);

    private static final String ENTITY_NAME = "subsector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsectorService subsectorService;

    public SubsectorResource(SubsectorService subsectorService) {
        this.subsectorService = subsectorService;
    }

    /**
     * {@code POST  /subsectors} : Create a new subsector.
     *
     * @param subsectorDTO the subsectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsectorDTO, or with status {@code 400 (Bad Request)} if the subsector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subsectors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SubsectorDTO> createSubsector(@Valid @RequestBody SubsectorDTO subsectorDTO) throws URISyntaxException {
        log.debug("REST request to save Subsector : {}", subsectorDTO);
        if (subsectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new subsector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubsectorDTO result = subsectorService.save(subsectorDTO);
        return ResponseEntity.created(new URI("/api/subsectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subsectors} : Updates an existing subsector.
     *
     * @param subsectorDTO the subsectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsectorDTO,
     * or with status {@code 400 (Bad Request)} if the subsectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subsectors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SubsectorDTO> updateSubsector(@Valid @RequestBody SubsectorDTO subsectorDTO) throws URISyntaxException {
        log.debug("REST request to update Subsector : {}", subsectorDTO);
        if (subsectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubsectorDTO result = subsectorService.save(subsectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subsectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subsectors} : get all the subsectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsectors in body.
     */
    @GetMapping("/subsectors")
    public List<SubsectorDTO> getAllSubsectors() {
        log.debug("REST request to get all Subsectors");
        return subsectorService.findAll();
    }

    /**
     * {@code GET  /subsectors/:id} : get the "id" subsector.
     *
     * @param id the id of the subsectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subsectors/{id}")
    public ResponseEntity<SubsectorDTO> getSubsector(@PathVariable Long id) {
        log.debug("REST request to get Subsector : {}", id);
        Optional<SubsectorDTO> subsectorDTO = subsectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subsectorDTO);
    }

    /**
     * {@code DELETE  /subsectors/:id} : delete the "id" subsector.
     *
     * @param id the id of the subsectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subsectors/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSubsector(@PathVariable Long id) {
        log.debug("REST request to delete Subsector : {}", id);
        subsectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
