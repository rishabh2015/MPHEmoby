package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Subsector;
import com.emoby.mph.repository.SubsectorRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.SubsectorService;
import com.emoby.mph.service.dto.SubsectorDTO;
import com.emoby.mph.service.mapper.SubsectorMapper;

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
 * Integration tests for the {@link SubsectorResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class SubsectorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private SubsectorRepository subsectorRepository;

    @Autowired
    private SubsectorMapper subsectorMapper;

    @Autowired
    private SubsectorService subsectorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsectorMockMvc;

    private Subsector subsector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsector createEntity(EntityManager em) {
        Subsector subsector = new Subsector()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return subsector;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subsector createUpdatedEntity(EntityManager em) {
        Subsector subsector = new Subsector()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return subsector;
    }

    @BeforeEach
    public void initTest() {
        subsector = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubsector() throws Exception {
        int databaseSizeBeforeCreate = subsectorRepository.findAll().size();
        // Create the Subsector
        SubsectorDTO subsectorDTO = subsectorMapper.toDto(subsector);
        restSubsectorMockMvc.perform(post("/api/subsectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Subsector in the database
        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeCreate + 1);
        Subsector testSubsector = subsectorList.get(subsectorList.size() - 1);
        assertThat(testSubsector.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSubsector.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createSubsectorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subsectorRepository.findAll().size();

        // Create the Subsector with an existing ID
        subsector.setId(1L);
        SubsectorDTO subsectorDTO = subsectorMapper.toDto(subsector);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsectorMockMvc.perform(post("/api/subsectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subsector in the database
        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subsectorRepository.findAll().size();
        // set the field null
        subsector.setCode(null);

        // Create the Subsector, which fails.
        SubsectorDTO subsectorDTO = subsectorMapper.toDto(subsector);


        restSubsectorMockMvc.perform(post("/api/subsectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsectorDTO)))
            .andExpect(status().isBadRequest());

        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = subsectorRepository.findAll().size();
        // set the field null
        subsector.setLibelle(null);

        // Create the Subsector, which fails.
        SubsectorDTO subsectorDTO = subsectorMapper.toDto(subsector);


        restSubsectorMockMvc.perform(post("/api/subsectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsectorDTO)))
            .andExpect(status().isBadRequest());

        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubsectors() throws Exception {
        // Initialize the database
        subsectorRepository.saveAndFlush(subsector);

        // Get all the subsectorList
        restSubsectorMockMvc.perform(get("/api/subsectors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsector.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getSubsector() throws Exception {
        // Initialize the database
        subsectorRepository.saveAndFlush(subsector);

        // Get the subsector
        restSubsectorMockMvc.perform(get("/api/subsectors/{id}", subsector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsector.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingSubsector() throws Exception {
        // Get the subsector
        restSubsectorMockMvc.perform(get("/api/subsectors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubsector() throws Exception {
        // Initialize the database
        subsectorRepository.saveAndFlush(subsector);

        int databaseSizeBeforeUpdate = subsectorRepository.findAll().size();

        // Update the subsector
        Subsector updatedSubsector = subsectorRepository.findById(subsector.getId()).get();
        // Disconnect from session so that the updates on updatedSubsector are not directly saved in db
        em.detach(updatedSubsector);
        updatedSubsector
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        SubsectorDTO subsectorDTO = subsectorMapper.toDto(updatedSubsector);

        restSubsectorMockMvc.perform(put("/api/subsectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsectorDTO)))
            .andExpect(status().isOk());

        // Validate the Subsector in the database
        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeUpdate);
        Subsector testSubsector = subsectorList.get(subsectorList.size() - 1);
        assertThat(testSubsector.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSubsector.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSubsector() throws Exception {
        int databaseSizeBeforeUpdate = subsectorRepository.findAll().size();

        // Create the Subsector
        SubsectorDTO subsectorDTO = subsectorMapper.toDto(subsector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsectorMockMvc.perform(put("/api/subsectors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subsector in the database
        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubsector() throws Exception {
        // Initialize the database
        subsectorRepository.saveAndFlush(subsector);

        int databaseSizeBeforeDelete = subsectorRepository.findAll().size();

        // Delete the subsector
        restSubsectorMockMvc.perform(delete("/api/subsectors/{id}", subsector.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subsector> subsectorList = subsectorRepository.findAll();
        assertThat(subsectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
