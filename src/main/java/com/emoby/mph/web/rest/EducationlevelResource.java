package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.EducationlevelService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.EducationlevelDTO;

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
 * REST controller for managing {@link com.emoby.mph.domain.Educationlevel}.
 */
@RestController
@RequestMapping("/api")
public class EducationlevelResource {

    private final Logger log = LoggerFactory.getLogger(EducationlevelResource.class);

    private static final String ENTITY_NAME = "educationlevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationlevelService educationlevelService;

    public EducationlevelResource(EducationlevelService educationlevelService) {
        this.educationlevelService = educationlevelService;
    }

    /**
     * {@code POST  /educationlevels} : Create a new educationlevel.
     *
     * @param educationlevelDTO the educationlevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationlevelDTO, or with status {@code 400 (Bad Request)} if the educationlevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educationlevels")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<EducationlevelDTO> createEducationlevel(@Valid @RequestBody EducationlevelDTO educationlevelDTO) throws URISyntaxException {
        log.debug("REST request to save Educationlevel : {}", educationlevelDTO);
        if (educationlevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new educationlevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationlevelDTO result = educationlevelService.save(educationlevelDTO);
        return ResponseEntity.created(new URI("/api/educationlevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /educationlevels} : Updates an existing educationlevel.
     *
     * @param educationlevelDTO the educationlevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationlevelDTO,
     * or with status {@code 400 (Bad Request)} if the educationlevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationlevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educationlevels")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<EducationlevelDTO> updateEducationlevel(@Valid @RequestBody EducationlevelDTO educationlevelDTO) throws URISyntaxException {
        log.debug("REST request to update Educationlevel : {}", educationlevelDTO);
        if (educationlevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EducationlevelDTO result = educationlevelService.save(educationlevelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, educationlevelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /educationlevels} : get all the educationlevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationlevels in body.
     */
    @GetMapping("/educationlevels")
    public List<EducationlevelDTO> getAllEducationlevels() {
        log.debug("REST request to get all Educationlevels");
        return educationlevelService.findAll();
    }

    /**
     * {@code GET  /educationlevels/:id} : get the "id" educationlevel.
     *
     * @param id the id of the educationlevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationlevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educationlevels/{id}")
    public ResponseEntity<EducationlevelDTO> getEducationlevel(@PathVariable Long id) {
        log.debug("REST request to get Educationlevel : {}", id);
        Optional<EducationlevelDTO> educationlevelDTO = educationlevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationlevelDTO);
    }

    /**
     * {@code DELETE  /educationlevels/:id} : delete the "id" educationlevel.
     *
     * @param id the id of the educationlevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educationlevels/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteEducationlevel(@PathVariable Long id) {
        log.debug("REST request to delete Educationlevel : {}", id);
        educationlevelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
