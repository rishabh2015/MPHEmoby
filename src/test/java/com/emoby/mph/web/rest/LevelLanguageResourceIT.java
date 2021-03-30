package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.LevelLanguage;
import com.emoby.mph.repository.LevelLanguageRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.LevelLanguageService;
import com.emoby.mph.service.dto.LevelLanguageDTO;
import com.emoby.mph.service.mapper.LevelLanguageMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LevelLanguageResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class LevelLanguageResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private LevelLanguageRepository levelLanguageRepository;

    @Autowired
    private LevelLanguageMapper levelLanguageMapper;

    @Autowired
    private LevelLanguageService levelLanguageService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLevelLanguageMockMvc;

    private LevelLanguage levelLanguage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelLanguage createEntity(EntityManager em) {
        LevelLanguage levelLanguage = new LevelLanguage()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return levelLanguage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelLanguage createUpdatedEntity(EntityManager em) {
        LevelLanguage levelLanguage = new LevelLanguage()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return levelLanguage;
    }

    @BeforeEach
    public void initTest() {
        levelLanguage = createEntity(em);
    }

    @Test
    @Transactional
    public void createLevelLanguage() throws Exception {
        int databaseSizeBeforeCreate = levelLanguageRepository.findAll().size();
        // Create the LevelLanguage
        LevelLanguageDTO levelLanguageDTO = levelLanguageMapper.toDto(levelLanguage);
        restLevelLanguageMockMvc.perform(post("/api/level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(levelLanguageDTO)))
            .andExpect(status().isCreated());

        // Validate the LevelLanguage in the database
        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeCreate + 1);
        LevelLanguage testLevelLanguage = levelLanguageList.get(levelLanguageList.size() - 1);
        assertThat(testLevelLanguage.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLevelLanguage.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createLevelLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = levelLanguageRepository.findAll().size();

        // Create the LevelLanguage with an existing ID
        levelLanguage.setId(1L);
        LevelLanguageDTO levelLanguageDTO = levelLanguageMapper.toDto(levelLanguage);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelLanguageMockMvc.perform(post("/api/level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(levelLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LevelLanguage in the database
        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelLanguageRepository.findAll().size();
        // set the field null
        levelLanguage.setCode(null);

        // Create the LevelLanguage, which fails.
        LevelLanguageDTO levelLanguageDTO = levelLanguageMapper.toDto(levelLanguage);


        restLevelLanguageMockMvc.perform(post("/api/level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(levelLanguageDTO)))
            .andExpect(status().isBadRequest());

        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = levelLanguageRepository.findAll().size();
        // set the field null
        levelLanguage.setLibelle(null);

        // Create the LevelLanguage, which fails.
        LevelLanguageDTO levelLanguageDTO = levelLanguageMapper.toDto(levelLanguage);


        restLevelLanguageMockMvc.perform(post("/api/level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(levelLanguageDTO)))
            .andExpect(status().isBadRequest());

        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLevelLanguages() throws Exception {
        // Initialize the database
        levelLanguageRepository.saveAndFlush(levelLanguage);

        // Get all the levelLanguageList
        restLevelLanguageMockMvc.perform(get("/api/level-languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelLanguage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getLevelLanguage() throws Exception {
        // Initialize the database
        levelLanguageRepository.saveAndFlush(levelLanguage);

        // Get the levelLanguage
        restLevelLanguageMockMvc.perform(get("/api/level-languages/{id}", levelLanguage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(levelLanguage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingLevelLanguage() throws Exception {
        // Get the levelLanguage
        restLevelLanguageMockMvc.perform(get("/api/level-languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevelLanguage() throws Exception {
        // Initialize the database
        levelLanguageRepository.saveAndFlush(levelLanguage);

        int databaseSizeBeforeUpdate = levelLanguageRepository.findAll().size();

        // Update the levelLanguage
        LevelLanguage updatedLevelLanguage = levelLanguageRepository.findById(levelLanguage.getId()).get();
        // Disconnect from session so that the updates on updatedLevelLanguage are not directly saved in db
        em.detach(updatedLevelLanguage);
        updatedLevelLanguage
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        LevelLanguageDTO levelLanguageDTO = levelLanguageMapper.toDto(updatedLevelLanguage);

        restLevelLanguageMockMvc.perform(put("/api/level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(levelLanguageDTO)))
            .andExpect(status().isOk());

        // Validate the LevelLanguage in the database
        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeUpdate);
        LevelLanguage testLevelLanguage = levelLanguageList.get(levelLanguageList.size() - 1);
        assertThat(testLevelLanguage.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLevelLanguage.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingLevelLanguage() throws Exception {
        int databaseSizeBeforeUpdate = levelLanguageRepository.findAll().size();

        // Create the LevelLanguage
        LevelLanguageDTO levelLanguageDTO = levelLanguageMapper.toDto(levelLanguage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLevelLanguageMockMvc.perform(put("/api/level-languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(levelLanguageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LevelLanguage in the database
        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLevelLanguage() throws Exception {
        // Initialize the database
        levelLanguageRepository.saveAndFlush(levelLanguage);

        int databaseSizeBeforeDelete = levelLanguageRepository.findAll().size();

        // Delete the levelLanguage
        restLevelLanguageMockMvc.perform(delete("/api/level-languages/{id}", levelLanguage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LevelLanguage> levelLanguageList = levelLanguageRepository.findAll();
        assertThat(levelLanguageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
