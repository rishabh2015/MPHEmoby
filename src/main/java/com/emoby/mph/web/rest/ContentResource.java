package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.ContentService;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.service.dto.ContentDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.emoby.mph.domain.Content}.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);

    private static final String ENTITY_NAME = "content";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentService contentService;

    public ContentResource(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * {@code POST  /contents} : Create a new content.
     *
     * @param contentDTO the contentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentDTO, or with status {@code 400 (Bad Request)} if the content has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contents")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ContentDTO> createContent(@RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to save Content : {}", contentDTO);
        if (contentDTO.getId() != null) {
            throw new BadRequestAlertException("A new content cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity.created(new URI("/api/contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contents} : Updates an existing content.
     *
     * @param contentDTO the contentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentDTO,
     * or with status {@code 400 (Bad Request)} if the contentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contents")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<ContentDTO> updateContent(@RequestBody ContentDTO contentDTO) throws URISyntaxException {
        log.debug("REST request to update Content : {}", contentDTO);
        if (contentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentDTO result = contentService.save(contentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contents} : get all the contents.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contents in body.
     */
    @GetMapping("/contents")
    public List<ContentDTO> getAllContents() {
        log.debug("REST request to get all Contents");
        return contentService.findAll();
    }

    /**
     * {@code GET  /contents/:id} : get the "id" content.
     *
     * @param id the id of the contentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contents/{id}")
    public ResponseEntity<ContentDTO> getContent(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        Optional<ContentDTO> contentDTO = contentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentDTO);
    }

    /**
     * {@code DELETE  /contents/:id} : delete the "id" content.
     *
     * @param id the id of the contentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contents/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
