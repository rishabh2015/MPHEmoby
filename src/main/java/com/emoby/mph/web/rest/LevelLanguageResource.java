package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.LevelLanguageService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.LevelLanguageDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.LevelLanguage}.
 */
@RestController
@RequestMapping("/api")
public class LevelLanguageResource {

    private final Logger log = LoggerFactory.getLogger(LevelLanguageResource.class);

    private static final String ENTITY_NAME = "levelLanguage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelLanguageService levelLanguageService;

    public LevelLanguageResource(LevelLanguageService levelLanguageService) {
        this.levelLanguageService = levelLanguageService;
    }

    /**
     * {@code POST  /level-languages} : Create a new levelLanguage.
     *
     * @param levelLanguageDTO the levelLanguageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelLanguageDTO, or with status {@code 400 (Bad Request)} if the levelLanguage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/level-languages")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<LevelLanguageDTO> createLevelLanguage(@Valid @RequestBody LevelLanguageDTO levelLanguageDTO) throws URISyntaxException {
        log.debug("REST request to save LevelLanguage : {}", levelLanguageDTO);
        if (levelLanguageDTO.getId() != null) {
            throw new BadRequestAlertException("A new levelLanguage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelLanguageDTO result = levelLanguageService.save(levelLanguageDTO);
        return ResponseEntity.created(new URI("/api/level-languages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /level-languages} : Updates an existing levelLanguage.
     *
     * @param levelLanguageDTO the levelLanguageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelLanguageDTO,
     * or with status {@code 400 (Bad Request)} if the levelLanguageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelLanguageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/level-languages")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<LevelLanguageDTO> updateLevelLanguage(@Valid @RequestBody LevelLanguageDTO levelLanguageDTO) throws URISyntaxException {
        log.debug("REST request to update LevelLanguage : {}", levelLanguageDTO);
        if (levelLanguageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LevelLanguageDTO result = levelLanguageService.save(levelLanguageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, levelLanguageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /level-languages} : get all the levelLanguages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levelLanguages in body.
     */
    @GetMapping("/level-languages")
    public List<LevelLanguageDTO> getAllLevelLanguages() {
        log.debug("REST request to get all LevelLanguages");
        return levelLanguageService.findAll();
    }

    /**
     * {@code GET  /level-languages/:id} : get the "id" levelLanguage.
     *
     * @param id the id of the levelLanguageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelLanguageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/level-languages/{id}")
    public ResponseEntity<LevelLanguageDTO> getLevelLanguage(@PathVariable Long id) {
        log.debug("REST request to get LevelLanguage : {}", id);
        Optional<LevelLanguageDTO> levelLanguageDTO = levelLanguageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelLanguageDTO);
    }

    /**
     * {@code DELETE  /level-languages/:id} : delete the "id" levelLanguage.
     *
     * @param id the id of the levelLanguageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/level-languages/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteLevelLanguage(@PathVariable Long id) {
        log.debug("REST request to delete LevelLanguage : {}", id);
        levelLanguageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
