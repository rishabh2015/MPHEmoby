package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.SectorService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.SectorDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.Sector}.
 */
@RestController
@RequestMapping("/api")
public class SectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorResource.class);

    private static final String ENTITY_NAME = "sector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SectorService sectorService;

    public SectorResource(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    /**
     * {@code POST  /sectors} : Create a new sector.
     *
     * @param sectorDTO the sectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sectorDTO, or with status {@code 400 (Bad Request)} if the sector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sectors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SectorDTO> createSector(@Valid @RequestBody SectorDTO sectorDTO) throws URISyntaxException {
        log.debug("REST request to save Sector : {}", sectorDTO);
        if (sectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectorDTO result = sectorService.save(sectorDTO);
        return ResponseEntity.created(new URI("/api/sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sectors} : Updates an existing sector.
     *
     * @param sectorDTO the sectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sectorDTO,
     * or with status {@code 400 (Bad Request)} if the sectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sectors")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<SectorDTO> updateSector(@Valid @RequestBody SectorDTO sectorDTO) throws URISyntaxException {
        log.debug("REST request to update Sector : {}", sectorDTO);
        if (sectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SectorDTO result = sectorService.save(sectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sectors} : get all the sectors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sectors in body.
     */
    @GetMapping("/sectors")
    public List<SectorDTO> getAllSectors() {
        log.debug("REST request to get all Sectors");
        return sectorService.findAll();
    }

    /**
     * {@code GET  /sectors/:id} : get the "id" sector.
     *
     * @param id the id of the sectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sectors/{id}")
    public ResponseEntity<SectorDTO> getSector(@PathVariable Long id) {
        log.debug("REST request to get Sector : {}", id);
        Optional<SectorDTO> sectorDTO = sectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sectorDTO);
    }

    /**
     * {@code DELETE  /sectors/:id} : delete the "id" sector.
     *
     * @param id the id of the sectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sectors/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        log.debug("REST request to delete Sector : {}", id);
        sectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
