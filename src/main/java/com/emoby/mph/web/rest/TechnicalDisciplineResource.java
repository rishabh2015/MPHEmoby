package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.TechnicalDisciplineService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.TechnicalDisciplineDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.TechnicalDiscipline}.
 */
@RestController
@RequestMapping("/api")
public class TechnicalDisciplineResource {

    private final Logger log = LoggerFactory.getLogger(TechnicalDisciplineResource.class);

    private static final String ENTITY_NAME = "technicalDiscipline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechnicalDisciplineService technicalDisciplineService;

    public TechnicalDisciplineResource(TechnicalDisciplineService technicalDisciplineService) {
        this.technicalDisciplineService = technicalDisciplineService;
    }

    /**
     * {@code POST  /technical-disciplines} : Create a new technicalDiscipline.
     *
     * @param technicalDisciplineDTO the technicalDisciplineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new technicalDisciplineDTO, or with status {@code 400 (Bad Request)} if the technicalDiscipline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/technical-disciplines")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TechnicalDisciplineDTO> createTechnicalDiscipline(@Valid @RequestBody TechnicalDisciplineDTO technicalDisciplineDTO) throws URISyntaxException {
        log.debug("REST request to save TechnicalDiscipline : {}", technicalDisciplineDTO);
        if (technicalDisciplineDTO.getId() != null) {
            throw new BadRequestAlertException("A new technicalDiscipline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechnicalDisciplineDTO result = technicalDisciplineService.save(technicalDisciplineDTO);
        return ResponseEntity.created(new URI("/api/technical-disciplines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /technical-disciplines} : Updates an existing technicalDiscipline.
     *
     * @param technicalDisciplineDTO the technicalDisciplineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technicalDisciplineDTO,
     * or with status {@code 400 (Bad Request)} if the technicalDisciplineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the technicalDisciplineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/technical-disciplines")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TechnicalDisciplineDTO> updateTechnicalDiscipline(@Valid @RequestBody TechnicalDisciplineDTO technicalDisciplineDTO) throws URISyntaxException {
        log.debug("REST request to update TechnicalDiscipline : {}", technicalDisciplineDTO);
        if (technicalDisciplineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechnicalDisciplineDTO result = technicalDisciplineService.save(technicalDisciplineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, technicalDisciplineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /technical-disciplines} : get all the technicalDisciplines.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of technicalDisciplines in body.
     */
    @GetMapping("/technical-disciplines")
    public List<TechnicalDisciplineDTO> getAllTechnicalDisciplines() {
        log.debug("REST request to get all TechnicalDisciplines");
        return technicalDisciplineService.findAll();
    }

    /**
     * {@code GET  /technical-disciplines/:id} : get the "id" technicalDiscipline.
     *
     * @param id the id of the technicalDisciplineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the technicalDisciplineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/technical-disciplines/{id}")
    public ResponseEntity<TechnicalDisciplineDTO> getTechnicalDiscipline(@PathVariable Long id) {
        log.debug("REST request to get TechnicalDiscipline : {}", id);
        Optional<TechnicalDisciplineDTO> technicalDisciplineDTO = technicalDisciplineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technicalDisciplineDTO);
    }

    /**
     * {@code DELETE  /technical-disciplines/:id} : delete the "id" technicalDiscipline.
     *
     * @param id the id of the technicalDisciplineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/technical-disciplines/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteTechnicalDiscipline(@PathVariable Long id) {
        log.debug("REST request to delete TechnicalDiscipline : {}", id);
        technicalDisciplineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
