package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.SectorsubsectorService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.SectorsubsectorDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.SectorSubsector}.
 */
@RestController
@RequestMapping("/api")
public class SectorsubsectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorsubsectorResource.class);

    private static final String ENTITY_NAME = "sectorsubsector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SectorsubsectorService sectorsubsectorService;

    public SectorsubsectorResource(SectorsubsectorService sectorsubsectorService) {
        this.sectorsubsectorService = sectorsubsectorService;
    }

    /**
     * {@code POST  /sectorsubsectors} : Create a new sectorsubsector.
     *
     * @param sectorsubsectorDTO the sectorsubsectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sectorsubsectorDTO, or with status {@code 400 (Bad Request)} if the sectorsubsector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sectorsubsectors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SectorsubsectorDTO> createSectorsubsector(@Valid @RequestBody SectorsubsectorDTO sectorsubsectorDTO) throws URISyntaxException {
        log.debug("REST request to save Sectorsubsector : {}", sectorsubsectorDTO);
        if (sectorsubsectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sectorsubsector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectorsubsectorDTO result = sectorsubsectorService.save(sectorsubsectorDTO);
        return ResponseEntity.created(new URI("/api/sectorsubsectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sectorsubsectors} : Updates an existing sectorsubsector.
     *
     * @param sectorsubsectorDTO the sectorsubsectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sectorsubsectorDTO,
     * or with status {@code 400 (Bad Request)} if the sectorsubsectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sectorsubsectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sectorsubsectors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SectorsubsectorDTO> updateSectorsubsector(@Valid @RequestBody SectorsubsectorDTO sectorsubsectorDTO) throws URISyntaxException {
        log.debug("REST request to update Sectorsubsector : {}", sectorsubsectorDTO);
        if (sectorsubsectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SectorsubsectorDTO result = sectorsubsectorService.save(sectorsubsectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sectorsubsectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sectorsubsectors} : get all the sectorsubsectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sectorsubsectors in body.
     */
    @GetMapping("/sectorsubsectors")
    public List<SectorsubsectorDTO> getAllSectorsubsectors() {
        log.debug("REST request to get all Sectorsubsectors");
        return sectorsubsectorService.findAll();
    }

    /**
     * {@code GET  /sectorsubsectors/:id} : get the "id" sectorsubsector.
     *
     * @param id the id of the sectorsubsectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sectorsubsectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sectorsubsectors/{id}")
    public ResponseEntity<SectorsubsectorDTO> getSectorsubsector(@PathVariable Long id) {
        log.debug("REST request to get Sectorsubsector : {}", id);
        Optional<SectorsubsectorDTO> sectorsubsectorDTO = sectorsubsectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sectorsubsectorDTO);
    }

    /**
     * {@code DELETE  /sectorsubsectors/:id} : delete the "id" sectorsubsector.
     *
     * @param id the id of the sectorsubsectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sectorsubsectors/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSectorsubsector(@PathVariable Long id) {
        log.debug("REST request to delete Sectorsubsector : {}", id);
        sectorsubsectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
