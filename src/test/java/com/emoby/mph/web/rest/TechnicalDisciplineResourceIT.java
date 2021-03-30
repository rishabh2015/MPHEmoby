package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.TechnicalDiscipline;
import com.emoby.mph.repository.TechnicalDisciplineRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.TechnicalDisciplineService;
import com.emoby.mph.service.dto.TechnicalDisciplineDTO;
import com.emoby.mph.service.mapper.TechnicalDisciplineMapper;

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
 * Integration tests for the {@link TechnicalDisciplineResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class TechnicalDisciplineResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private TechnicalDisciplineRepository technicalDisciplineRepository;

    @Autowired
    private TechnicalDisciplineMapper technicalDisciplineMapper;

    @Autowired
    private TechnicalDisciplineService technicalDisciplineService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTechnicalDisciplineMockMvc;

    private TechnicalDiscipline technicalDiscipline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnicalDiscipline createEntity(EntityManager em) {
        TechnicalDiscipline technicalDiscipline = new TechnicalDiscipline()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return technicalDiscipline;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechnicalDiscipline createUpdatedEntity(EntityManager em) {
        TechnicalDiscipline technicalDiscipline = new TechnicalDiscipline()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return technicalDiscipline;
    }

    @BeforeEach
    public void initTest() {
        technicalDiscipline = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechnicalDiscipline() throws Exception {
        int databaseSizeBeforeCreate = technicalDisciplineRepository.findAll().size();
        // Create the TechnicalDiscipline
        TechnicalDisciplineDTO technicalDisciplineDTO = technicalDisciplineMapper.toDto(technicalDiscipline);
        restTechnicalDisciplineMockMvc.perform(post("/api/technical-disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technicalDisciplineDTO)))
            .andExpect(status().isCreated());

        // Validate the TechnicalDiscipline in the database
        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeCreate + 1);
        TechnicalDiscipline testTechnicalDiscipline = technicalDisciplineList.get(technicalDisciplineList.size() - 1);
        assertThat(testTechnicalDiscipline.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTechnicalDiscipline.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createTechnicalDisciplineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technicalDisciplineRepository.findAll().size();

        // Create the TechnicalDiscipline with an existing ID
        technicalDiscipline.setId(1L);
        TechnicalDisciplineDTO technicalDisciplineDTO = technicalDisciplineMapper.toDto(technicalDiscipline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnicalDisciplineMockMvc.perform(post("/api/technical-disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technicalDisciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechnicalDiscipline in the database
        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = technicalDisciplineRepository.findAll().size();
        // set the field null
        technicalDiscipline.setCode(null);

        // Create the TechnicalDiscipline, which fails.
        TechnicalDisciplineDTO technicalDisciplineDTO = technicalDisciplineMapper.toDto(technicalDiscipline);


        restTechnicalDisciplineMockMvc.perform(post("/api/technical-disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technicalDisciplineDTO)))
            .andExpect(status().isBadRequest());

        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = technicalDisciplineRepository.findAll().size();
        // set the field null
        technicalDiscipline.setLibelle(null);

        // Create the TechnicalDiscipline, which fails.
        TechnicalDisciplineDTO technicalDisciplineDTO = technicalDisciplineMapper.toDto(technicalDiscipline);


        restTechnicalDisciplineMockMvc.perform(post("/api/technical-disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technicalDisciplineDTO)))
            .andExpect(status().isBadRequest());

        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnicalDisciplines() throws Exception {
        // Initialize the database
        technicalDisciplineRepository.saveAndFlush(technicalDiscipline);

        // Get all the technicalDisciplineList
        restTechnicalDisciplineMockMvc.perform(get("/api/technical-disciplines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(technicalDiscipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getTechnicalDiscipline() throws Exception {
        // Initialize the database
        technicalDisciplineRepository.saveAndFlush(technicalDiscipline);

        // Get the technicalDiscipline
        restTechnicalDisciplineMockMvc.perform(get("/api/technical-disciplines/{id}", technicalDiscipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(technicalDiscipline.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingTechnicalDiscipline() throws Exception {
        // Get the technicalDiscipline
        restTechnicalDisciplineMockMvc.perform(get("/api/technical-disciplines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechnicalDiscipline() throws Exception {
        // Initialize the database
        technicalDisciplineRepository.saveAndFlush(technicalDiscipline);

        int databaseSizeBeforeUpdate = technicalDisciplineRepository.findAll().size();

        // Update the technicalDiscipline
        TechnicalDiscipline updatedTechnicalDiscipline = technicalDisciplineRepository.findById(technicalDiscipline.getId()).get();
        // Disconnect from session so that the updates on updatedTechnicalDiscipline are not directly saved in db
        em.detach(updatedTechnicalDiscipline);
        updatedTechnicalDiscipline
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        TechnicalDisciplineDTO technicalDisciplineDTO = technicalDisciplineMapper.toDto(updatedTechnicalDiscipline);

        restTechnicalDisciplineMockMvc.perform(put("/api/technical-disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technicalDisciplineDTO)))
            .andExpect(status().isOk());

        // Validate the TechnicalDiscipline in the database
        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeUpdate);
        TechnicalDiscipline testTechnicalDiscipline = technicalDisciplineList.get(technicalDisciplineList.size() - 1);
        assertThat(testTechnicalDiscipline.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTechnicalDiscipline.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingTechnicalDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = technicalDisciplineRepository.findAll().size();

        // Create the TechnicalDiscipline
        TechnicalDisciplineDTO technicalDisciplineDTO = technicalDisciplineMapper.toDto(technicalDiscipline);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnicalDisciplineMockMvc.perform(put("/api/technical-disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technicalDisciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechnicalDiscipline in the database
        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechnicalDiscipline() throws Exception {
        // Initialize the database
        technicalDisciplineRepository.saveAndFlush(technicalDiscipline);

        int databaseSizeBeforeDelete = technicalDisciplineRepository.findAll().size();

        // Delete the technicalDiscipline
        restTechnicalDisciplineMockMvc.perform(delete("/api/technical-disciplines/{id}", technicalDiscipline.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechnicalDiscipline> technicalDisciplineList = technicalDisciplineRepository.findAll();
        assertThat(technicalDisciplineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
