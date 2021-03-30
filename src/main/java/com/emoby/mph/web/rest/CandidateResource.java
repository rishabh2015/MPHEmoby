package com.emoby.mph.web.rest;

import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.CandidateService;
import com.emoby.mph.service.dao.MPHResponse;
import com.emoby.mph.service.dto.CandidateCommentDTO;
import com.emoby.mph.service.dto.CandidateDTO;
import com.emoby.mph.service.dto.MPHContentDTO;
import com.emoby.mph.service.dto.SearchCandidatesRequestDTO;
import com.emoby.mph.service.dto.SearchCandidatesResponseDTO;
import com.emoby.mph.web.rest.errors.BadRequestAlertException;
import com.emoby.mph.web.rest.errors.NoDataAlertException;
import com.emoby.mph.web.rest.errors.NoUpdateAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.emoby.mph.domain.Candidate}.
 */
@RestController
@RequestMapping("/api")
public class CandidateResource {
    private final Logger log = LoggerFactory.getLogger(CandidateResource.class);

    private static final String ENTITY_NAME = "candidate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateService candidateService;

    public CandidateResource(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     * {@code POST  /candidates} : Create a new candidate.
     *
     * @param candidateDTO the candidateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateDTO, or with status {@code 400 (Bad Request)} if the candidate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidates")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.FORBIDDEN + "\")")
    public ResponseEntity<CandidateDTO> createCandidate(@Valid @RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to save Candidate : {}", candidateDTO);
        if (candidateDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateDTO result = candidateService.save(candidateDTO);
        return ResponseEntity
            .created(new URI("/api/candidates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidates} : Updates an existing candidate.
     *
     * @param candidateDTO the candidateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateDTO,
     * or with status {@code 400 (Bad Request)} if the candidateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidates")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.FORBIDDEN + "\")")
    public ResponseEntity<CandidateDTO> updateCandidate(@Valid @RequestBody CandidateDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to update Candidate : {}", candidateDTO);
        if (candidateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CandidateDTO result = candidateService.save(candidateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /candidates} : get all the candidates.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidates in body.
     */
    @GetMapping("/candidates")
    public ResponseEntity<List<CandidateDTO>> getAllCandidates(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Candidates");
        Page<CandidateDTO> page;
        if (eagerload) {
            page = candidateService.findAllWithEagerRelationships(pageable);
        } else {
            page = candidateService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /candidates/:id} : get the "id" candidate.
     *
     * @param id the id of the candidateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidates/{id}")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        Optional<CandidateDTO> candidateDTO = candidateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(candidateDTO);
    }

    /**
     * {@code DELETE  /candidates/:id} : delete the "id" candidate.
     *
     * @param id the id of the candidateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.FORBIDDEN + "\")")
    @DeleteMapping("/candidates/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/setShortlisted")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> setShortlisted(
        @RequestParam(value = "candidateId") Long candidateId,
        @RequestParam(value = "jobdescriptionId", required = false) Long jobdescriptionId
    ) {
        log.debug("REST request to setShortlisted");
        int numberUpdate = candidateService.setShortlisted(candidateId, jobdescriptionId);

        if (numberUpdate == 0) {
            throw new NoUpdateAlertException("noUpdate", ENTITY_NAME, "noUpdate");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/setPlaced")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> setPlaced(
        @RequestParam(value = "candidateId") Long candidateId,
        @RequestParam(value = "jobdescriptionId", required = false) Long jobdescriptionId
    ) {
        log.debug("REST request to setPlaced");
        int numberUpdate = candidateService.setPlaced(candidateId, jobdescriptionId);

        if (numberUpdate == 0) {
            throw new NoUpdateAlertException("noUpdate", ENTITY_NAME, "noUpdate");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getCvByIdDocument")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public MPHContentDTO getCvByIdDocument(@RequestParam(value = "candidateId") Long candidateId) {
        log.debug("REST request to getCvByIdDocument");
        MPHContentDTO reponse = new MPHContentDTO();
        MPHResponse mphResponse = candidateService.getCvByIdDocument(candidateId);

        if (mphResponse == null || mphResponse.getContent() == null) {
            throw new NoDataAlertException("noData", ENTITY_NAME, "noData");
        } else {
            reponse.setData(mphResponse.getContent().getData());
            reponse.setDataContentType(mphResponse.getContentType());

            // On recupere le nomdu fichier dans : "https://assystemgroup.sharepoint.com/sites/Emirates/mphrm/mph_candidate/Good Day (134543)/CVs/lior construction - resume.doc?web=1"
            String filename = mphResponse.getContent().getUrl().substring(mphResponse.getContent().getUrl().lastIndexOf("/"));
            filename = filename.substring(1);
            filename = filename.replaceAll("\\?(.*)", "");
            reponse.setFilename(filename);
        }

        return reponse;
    }

    @PostMapping("/setCandidateComment")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.USER + "\")")
    public ResponseEntity<Void> setCandidateComment(@Valid @RequestBody CandidateCommentDTO candidateDTO) throws URISyntaxException {
        log.debug("REST request to setCandidateComment : {}", candidateDTO.getId());
        if (candidateDTO.getId() == null) {
            throw new BadRequestAlertException("NoID", ENTITY_NAME, "NoId");
        }
        candidateService.setCandidateComment(candidateDTO.getId(), candidateDTO.getComment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/searchCandidates")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\") ||  hasAuthority(\""+ AuthoritiesConstants.HR+ "\")")
    public List<SearchCandidatesResponseDTO> searchCandidates(@Valid @RequestBody SearchCandidatesRequestDTO searchCandidatesRequestDTO) throws URISyntaxException {
    	log.debug("REST request to searchCandidates");
    	List<SearchCandidatesResponseDTO> result = new ArrayList<SearchCandidatesResponseDTO>();
    	result = candidateService.searchCandidates(searchCandidatesRequestDTO);
    	
    	return result;
    }
}
