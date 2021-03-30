package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.CandidateLevelLanguageService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.CandidateLevelLanguageDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.CandidateLevelLanguage}.
 */
@RestController
@RequestMapping("/api")
public class CandidateLevelLanguageResource {

    private final Logger log = LoggerFactory.getLogger(CandidateLevelLanguageResource.class);

    private static final String ENTITY_NAME = "candidateLevelLanguage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateLevelLanguageService candidateLevelLanguageService;

    public CandidateLevelLanguageResource(CandidateLevelLanguageService candidateLevelLanguageService) {
        this.candidateLevelLanguageService = candidateLevelLanguageService;
    }

    /**
     * {@code POST  /candidate-level-languages} : Create a new candidateLevelLanguage.
     *
     * @param candidateLevelLanguageDTO the candidateLevelLanguageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateLevelLanguageDTO, or with status {@code 400 (Bad Request)} if the candidateLevelLanguage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidate-level-languages")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CandidateLevelLanguageDTO> createCandidateLevelLanguage(@Valid @RequestBody CandidateLevelLanguageDTO candidateLevelLanguageDTO) throws URISyntaxException {
        log.debug("REST request to save CandidateLevelLanguage : {}", candidateLevelLanguageDTO);
        if (candidateLevelLanguageDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateLevelLanguage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateLevelLanguageDTO result = candidateLevelLanguageService.save(candidateLevelLanguageDTO);
        return ResponseEntity.created(new URI("/api/candidate-level-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidate-level-languages} : Updates an existing candidateLevelLanguage.
     *
     * @param candidateLevelLanguageDTO the candidateLevelLanguageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateLevelLanguageDTO,
     * or with status {@code 400 (Bad Request)} if the candidateLevelLanguageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateLevelLanguageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidate-level-languages")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<CandidateLevelLanguageDTO> updateCandidateLevelLanguage(@Valid @RequestBody CandidateLevelLanguageDTO candidateLevelLanguageDTO) throws URISyntaxException {
        log.debug("REST request to update CandidateLevelLanguage : {}", candidateLevelLanguageDTO);
        if (candidateLevelLanguageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CandidateLevelLanguageDTO result = candidateLevelLanguageService.save(candidateLevelLanguageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateLevelLanguageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /candidate-level-languages} : get all the candidateLevelLanguages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidateLevelLanguages in body.
     */
    @GetMapping("/candidate-level-languages")
    public List<CandidateLevelLanguageDTO> getAllCandidateLevelLanguages() {
        log.debug("REST request to get all CandidateLevelLanguages");
        return candidateLevelLanguageService.findAll();
    }

    /**
     * {@code GET  /candidate-level-languages/:id} : get the "id" candidateLevelLanguage.
     *
     * @param id the id of the candidateLevelLanguageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateLevelLanguageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidate-level-languages/{id}")
    public ResponseEntity<CandidateLevelLanguageDTO> getCandidateLevelLanguage(@PathVariable Long id) {
        log.debug("REST request to get CandidateLevelLanguage : {}", id);
        Optional<CandidateLevelLanguageDTO> candidateLevelLanguageDTO = candidateLevelLanguageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidateLevelLanguageDTO);
    }

    /**
     * {@code DELETE  /candidate-level-languages/:id} : delete the "id" candidateLevelLanguage.
     *
     * @param id the id of the candidateLevelLanguageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidate-level-languages/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteCandidateLevelLanguage(@PathVariable Long id) {
        log.debug("REST request to delete CandidateLevelLanguage : {}", id);
        candidateLevelLanguageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
