package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.MobyStatusService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.MobyStatusDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.MobyStatus}.
 */
@RestController
@RequestMapping("/api")
public class MobyStatusResource {

    private final Logger log = LoggerFactory.getLogger(MobyStatusResource.class);

    private static final String ENTITY_NAME = "mobyStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MobyStatusService mobyStatusService;

    public MobyStatusResource(MobyStatusService mobyStatusService) {
        this.mobyStatusService = mobyStatusService;
    }

    /**
     * {@code POST  /moby-statuses} : Create a new mobyStatus.
     *
     * @param mobyStatusDTO the mobyStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobyStatusDTO, or with status {@code 400 (Bad Request)} if the mobyStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/moby-statuses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MobyStatusDTO> createMobyStatus(@Valid @RequestBody MobyStatusDTO mobyStatusDTO) throws URISyntaxException {
        log.debug("REST request to save MobyStatus : {}", mobyStatusDTO);
        if (mobyStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new mobyStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MobyStatusDTO result = mobyStatusService.save(mobyStatusDTO);
        return ResponseEntity.created(new URI("/api/moby-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /moby-statuses} : Updates an existing mobyStatus.
     *
     * @param mobyStatusDTO the mobyStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobyStatusDTO,
     * or with status {@code 400 (Bad Request)} if the mobyStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobyStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/moby-statuses")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MobyStatusDTO> updateMobyStatus(@Valid @RequestBody MobyStatusDTO mobyStatusDTO) throws URISyntaxException {
        log.debug("REST request to update MobyStatus : {}", mobyStatusDTO);
        if (mobyStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MobyStatusDTO result = mobyStatusService.save(mobyStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mobyStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /moby-statuses} : get all the mobyStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobyStatuses in body.
     */
    @GetMapping("/moby-statuses")
    public List<MobyStatusDTO> getAllMobyStatuses() {
        log.debug("REST request to get all MobyStatuses");
        return mobyStatusService.findAll();
    }

    /**
     * {@code GET  /moby-statuses/:id} : get the "id" mobyStatus.
     *
     * @param id the id of the mobyStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobyStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/moby-statuses/{id}")
    public ResponseEntity<MobyStatusDTO> getMobyStatus(@PathVariable Long id) {
        log.debug("REST request to get MobyStatus : {}", id);
        Optional<MobyStatusDTO> mobyStatusDTO = mobyStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mobyStatusDTO);
    }

    /**
     * {@code DELETE  /moby-statuses/:id} : delete the "id" mobyStatus.
     *
     * @param id the id of the mobyStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/moby-statuses/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteMobyStatus(@PathVariable Long id) {
        log.debug("REST request to delete MobyStatus : {}", id);
        mobyStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
