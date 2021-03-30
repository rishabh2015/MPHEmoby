package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.PotentialCandidateService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.JobdescriptionCompactDTO;
import com.emoby.mph.service.dto.PotentialCandidateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emoby.mph.domain.PotentialCandidate}.
 */
@RestController
@RequestMapping("/api")
public class PotentialCandidateResource {

    private final Logger log = LoggerFactory.getLogger(PotentialCandidateResource.class);

    private static final String ENTITY_NAME = "potentialCandidate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PotentialCandidateService potentialCandidateService;

    public PotentialCandidateResource(PotentialCandidateService potentialCandidateService) {
        this.potentialCandidateService = potentialCandidateService;
    }

    /**
     * {@code POST  /potential-candidates} : Create a new potentialCandidate.
     *
     * @param potentialCandidateDTO the potentialCandidateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potentialCandidateDTO, or with status {@code 400 (Bad Request)} if the potentialCandidate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/potential-candidates")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PotentialCandidateDTO> createPotentialCandidate(@Valid @RequestBody PotentialCandidateDTO potentialCandidateDTO) throws URISyntaxException {
        log.debug("REST request to save PotentialCandidate : {}", potentialCandidateDTO);
        if (potentialCandidateDTO.getId() != null) {
            throw new BadRequestAlertException("A new potentialCandidate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PotentialCandidateDTO result = potentialCandidateService.save(potentialCandidateDTO);
        return ResponseEntity.created(new URI("/api/potential-candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /potential-candidates} : Updates an existing potentialCandidate.
     *
     * @param potentialCandidateDTO the potentialCandidateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potentialCandidateDTO,
     * or with status {@code 400 (Bad Request)} if the potentialCandidateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the potentialCandidateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/potential-candidates")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<PotentialCandidateDTO> updatePotentialCandidate(@Valid @RequestBody PotentialCandidateDTO potentialCandidateDTO) throws URISyntaxException {
        log.debug("REST request to update PotentialCandidate : {}", potentialCandidateDTO);
        if (potentialCandidateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PotentialCandidateDTO result = potentialCandidateService.save(potentialCandidateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potentialCandidateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /potential-candidates} : get all the potentialCandidates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potentialCandidates in body.
     */
    @GetMapping("/potential-candidates")
    public List<PotentialCandidateDTO> getAllPotentialCandidates() {
        log.debug("REST request to get all PotentialCandidates");
        return potentialCandidateService.findAll();
    }

    /**
     * {@code GET  /potential-candidates/:id} : get the "id" potentialCandidate.
     *
     * @param id the id of the potentialCandidateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potentialCandidateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/potential-candidates/{id}")
    public ResponseEntity<PotentialCandidateDTO> getPotentialCandidate(@PathVariable Long id) {
        log.debug("REST request to get PotentialCandidate : {}", id);
        Optional<PotentialCandidateDTO> potentialCandidateDTO = potentialCandidateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(potentialCandidateDTO);
    }

    /**
     * {@code DELETE  /potential-candidates/:id} : delete the "id" potentialCandidate.
     *
     * @param id the id of the potentialCandidateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/potential-candidates/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deletePotentialCandidate(@PathVariable Long id) {
        log.debug("REST request to delete PotentialCandidate : {}", id);
        potentialCandidateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/potential-candidates/findAllPotentialCandidateByJobdescriptionId")
    public ResponseEntity<List<PotentialCandidateDTO>> findAllPotentialCandidateByJobdescriptionId(Pageable pageable,  @RequestParam(value = "jobdescriptionId") Long jobdescriptionId,  @RequestParam(value = "mode") String mode) throws URISyntaxException {
        log.debug("REST request to findAllPotentialCandidateByJobdescriptionId : {}", jobdescriptionId);
        Page<PotentialCandidateDTO> page;
        if (jobdescriptionId == null) {
            throw new BadRequestAlertException("Invalid jobdescriptionId", ENTITY_NAME, "jobdescriptionIdnull");
        }
        
        switch(mode) 
        { 
            case "shortlisted": 
            	page = potentialCandidateService.findAllPotentialCandidateShortlistedByJobdescriptionId(pageable, jobdescriptionId);
                break; 
            case "placed": 
                page = potentialCandidateService.findAllPotentialCandidatePlacedByJobdescriptionId(pageable, jobdescriptionId);
                break; 
            default: 
            	page = potentialCandidateService.findAllPotentialCandidateByJobdescriptionId(pageable, jobdescriptionId);
        }
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
