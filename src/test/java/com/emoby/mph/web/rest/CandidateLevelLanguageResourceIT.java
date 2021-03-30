package com.emoby.mph.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Candidate;
import com.emoby.mph.domain.CandidateLevelLanguage;
import com.emoby.mph.domain.Language;
import com.emoby.mph.domain.LevelLanguage;
import com.emoby.mph.repository.CandidateLevelLanguageRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.CandidateLevelLanguageService;
import com.emoby.mph.service.dto.CandidateDTO;
import com.emoby.mph.service.dto.CandidateLevelLanguageDTO;
import com.emoby.mph.service.mapper.CandidateLevelLanguageMapper;

/**
 * Integration tests for the {@link CandidateLevelLanguageResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class CandidateLevelLanguageResourceIT {

    @Autowired
    private CandidateLevelLanguageRepository candidateLevelLanguageRepository;

    @Autowired
    private CandidateLevelLanguageMapper candidateLevelLanguageMapper;

    @Autowired
    private CandidateLevelLanguageService candidateLevelLanguageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateLevelLanguageMockMvc;

    private CandidateLevelLanguage candidateLevelLanguage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateLevelLanguage createEntity(EntityManager em) {
    	
    	CandidateLevelLanguage candidateLevelLanguage = new CandidateLevelLanguage();
    	
    	// Add required entity
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidate = CandidateResourceIT.createEntity(em);
            em.persist(candidate);
            em.flush();
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        candidateLevelLanguage.setCandidate(candidate);
    	
        // Add required entity
        LevelLanguage levelLanguage;
        if (TestUtil.findAll(em, LevelLanguage.class).isEmpty()) {
            levelLanguage = LevelLanguageResourceIT.createEntity(em);
            em.persist(levelLanguage);
            em.flush();
        } else {
            levelLanguage = TestUtil.findAll(em, LevelLanguage.class).get(0);
        }
        candidateLevelLanguage.setLevelLanguage(levelLanguage);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        candidateLevelLanguage.setLanguage(language);
        return candidateLevelLanguage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CandidateLevelLanguage createUpdatedEntity(EntityManager em) {
        CandidateLevelLanguage candidateLevelLanguage = new CandidateLevelLanguage();
        // Add required entity
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidate = CandidateResourceIT.createEntity(em);
            em.persist(candidate);
            em.flush();
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        candidateLevelLanguage.setCandidate(candidate);

        
        // Add required entity
        LevelLanguage levelLanguage;
        if (TestUtil.findAll(em, LevelLanguage.class).isEmpty()) {
            levelLanguage = LevelLanguageResourceIT.createUpdatedEntity(em);
            em.persist(levelLanguage);
            em.flush();
        } else {
            levelLanguage = TestUtil.findAll(em, LevelLanguage.class).get(0);
        }
        candidateLevelLanguage.setLevelLanguage(levelLanguage);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createUpdatedEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        candidateLevelLanguage.setLanguage(language);
        return candidateLevelLanguage;
    }

    @BeforeEach
    public void initTest() {
        candidateLevelLanguage = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateLevelLanguage() throws Exception {
        int databaseSizeBeforeCreate = candidateLevelLanguageRepository.findAll().size();
        // Create the CandidateLevelLanguage
        CandidateLevelLanguageDTO candidateLevelLanguageDTO = candidateLevelLanguageMapper.toDto(candidateLevelLanguage);
        restCandidateLevelLanguageMockMvc.perform(post("/api/candidate-level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateLevelLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the CandidateLevelLanguage in the database
        List<CandidateLevelLanguage> candidateLevelLanguageList = candidateLevelLanguageRepository.findAll();
        assertThat(candidateLevelLanguageList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateLevelLanguage testCandidateLevelLanguage = candidateLevelLanguageList.get(candidateLevelLanguageList.size() - 1);
    }

    @Test
    @Transactional
    public void createCandidateLevelLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateLevelLanguageRepository.findAll().size();

        // Create the CandidateLevelLanguage with an existing ID
        candidateLevelLanguage.setId(1L);
        CandidateLevelLanguageDTO candidateLevelLanguageDTO = candidateLevelLanguageMapper.toDto(candidateLevelLanguage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateLevelLanguageMockMvc.perform(post("/api/candidate-level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateLevelLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateLevelLanguage in the database
        List<CandidateLevelLanguage> candidateLevelLanguageList = candidateLevelLanguageRepository.findAll();
        assertThat(candidateLevelLanguageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCandidateLevelLanguages() throws Exception {
        // Initialize the database
        candidateLevelLanguageRepository.saveAndFlush(candidateLevelLanguage);

        // Get all the candidateLevelLanguageList
        restCandidateLevelLanguageMockMvc.perform(get("/api/candidate-level-languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateLevelLanguage.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCandidateLevelLanguage() throws Exception {
        // Initialize the database
        candidateLevelLanguageRepository.saveAndFlush(candidateLevelLanguage);

        // Get the candidateLevelLanguage
        restCandidateLevelLanguageMockMvc.perform(get("/api/candidate-level-languages/{id}", candidateLevelLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidateLevelLanguage.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCandidateLevelLanguage() throws Exception {
        // Get the candidateLevelLanguage
        restCandidateLevelLanguageMockMvc.perform(get("/api/candidate-level-languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateLevelLanguage() throws Exception {
        // Initialize the database
        candidateLevelLanguageRepository.saveAndFlush(candidateLevelLanguage);

        int databaseSizeBeforeUpdate = candidateLevelLanguageRepository.findAll().size();

        // Update the candidateLevelLanguage
        CandidateLevelLanguage updatedCandidateLevelLanguage = candidateLevelLanguageRepository.findById(candidateLevelLanguage.getId()).get();
        // Disconnect from session so that the updates on updatedCandidateLevelLanguage are not directly saved in db
        em.detach(updatedCandidateLevelLanguage);
        CandidateLevelLanguageDTO candidateLevelLanguageDTO = candidateLevelLanguageMapper.toDto(updatedCandidateLevelLanguage);
        
        
        
        restCandidateLevelLanguageMockMvc.perform(put("/api/candidate-level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateLevelLanguageDTO)))
            .andExpect(status().isOk());

        // Validate the CandidateLevelLanguage in the database
        List<CandidateLevelLanguage> candidateLevelLanguageList = candidateLevelLanguageRepository.findAll();
        assertThat(candidateLevelLanguageList).hasSize(databaseSizeBeforeUpdate);
        CandidateLevelLanguage testCandidateLevelLanguage = candidateLevelLanguageList.get(candidateLevelLanguageList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateLevelLanguage() throws Exception {
        int databaseSizeBeforeUpdate = candidateLevelLanguageRepository.findAll().size();

        // Create the CandidateLevelLanguage
        CandidateLevelLanguageDTO candidateLevelLanguageDTO = candidateLevelLanguageMapper.toDto(candidateLevelLanguage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateLevelLanguageMockMvc.perform(put("/api/candidate-level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateLevelLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateLevelLanguage in the database
        List<CandidateLevelLanguage> candidateLevelLanguageList = candidateLevelLanguageRepository.findAll();
        assertThat(candidateLevelLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidateLevelLanguage() throws Exception {
        // Initialize the database
        candidateLevelLanguageRepository.saveAndFlush(candidateLevelLanguage);

        int databaseSizeBeforeDelete = candidateLevelLanguageRepository.findAll().size();

        // Delete the candidateLevelLanguage
        restCandidateLevelLanguageMockMvc.perform(delete("/api/candidate-level-languages/{id}", candidateLevelLanguage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CandidateLevelLanguage> candidateLevelLanguageList = candidateLevelLanguageRepository.findAll();
        assertThat(candidateLevelLanguageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
