package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.MobyStatus;
import com.emoby.mph.repository.MobyStatusRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.MobyStatusService;
import com.emoby.mph.service.dto.MobyStatusDTO;
import com.emoby.mph.service.mapper.MobyStatusMapper;

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
 * Integration tests for the {@link MobyStatusResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class MobyStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private MobyStatusRepository mobyStatusRepository;

    @Autowired
    private MobyStatusMapper mobyStatusMapper;

    @Autowired
    private MobyStatusService mobyStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMobyStatusMockMvc;

    private MobyStatus mobyStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobyStatus createEntity(EntityManager em) {
        MobyStatus mobyStatus = new MobyStatus()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return mobyStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MobyStatus createUpdatedEntity(EntityManager em) {
        MobyStatus mobyStatus = new MobyStatus()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return mobyStatus;
    }

    @BeforeEach
    public void initTest() {
        mobyStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMobyStatus() throws Exception {
        int databaseSizeBeforeCreate = mobyStatusRepository.findAll().size();
        // Create the MobyStatus
        MobyStatusDTO mobyStatusDTO = mobyStatusMapper.toDto(mobyStatus);
        restMobyStatusMockMvc.perform(post("/api/moby-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobyStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the MobyStatus in the database
        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MobyStatus testMobyStatus = mobyStatusList.get(mobyStatusList.size() - 1);
        assertThat(testMobyStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMobyStatus.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createMobyStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mobyStatusRepository.findAll().size();

        // Create the MobyStatus with an existing ID
        mobyStatus.setId(1L);
        MobyStatusDTO mobyStatusDTO = mobyStatusMapper.toDto(mobyStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMobyStatusMockMvc.perform(post("/api/moby-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobyStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MobyStatus in the database
        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobyStatusRepository.findAll().size();
        // set the field null
        mobyStatus.setCode(null);

        // Create the MobyStatus, which fails.
        MobyStatusDTO mobyStatusDTO = mobyStatusMapper.toDto(mobyStatus);


        restMobyStatusMockMvc.perform(post("/api/moby-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobyStatusDTO)))
            .andExpect(status().isBadRequest());

        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = mobyStatusRepository.findAll().size();
        // set the field null
        mobyStatus.setLibelle(null);

        // Create the MobyStatus, which fails.
        MobyStatusDTO mobyStatusDTO = mobyStatusMapper.toDto(mobyStatus);


        restMobyStatusMockMvc.perform(post("/api/moby-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobyStatusDTO)))
            .andExpect(status().isBadRequest());

        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMobyStatuses() throws Exception {
        // Initialize the database
        mobyStatusRepository.saveAndFlush(mobyStatus);

        // Get all the mobyStatusList
        restMobyStatusMockMvc.perform(get("/api/moby-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mobyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getMobyStatus() throws Exception {
        // Initialize the database
        mobyStatusRepository.saveAndFlush(mobyStatus);

        // Get the mobyStatus
        restMobyStatusMockMvc.perform(get("/api/moby-statuses/{id}", mobyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mobyStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingMobyStatus() throws Exception {
        // Get the mobyStatus
        restMobyStatusMockMvc.perform(get("/api/moby-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMobyStatus() throws Exception {
        // Initialize the database
        mobyStatusRepository.saveAndFlush(mobyStatus);

        int databaseSizeBeforeUpdate = mobyStatusRepository.findAll().size();

        // Update the mobyStatus
        MobyStatus updatedMobyStatus = mobyStatusRepository.findById(mobyStatus.getId()).get();
        // Disconnect from session so that the updates on updatedMobyStatus are not directly saved in db
        em.detach(updatedMobyStatus);
        updatedMobyStatus
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        MobyStatusDTO mobyStatusDTO = mobyStatusMapper.toDto(updatedMobyStatus);

        restMobyStatusMockMvc.perform(put("/api/moby-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobyStatusDTO)))
            .andExpect(status().isOk());

        // Validate the MobyStatus in the database
        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeUpdate);
        MobyStatus testMobyStatus = mobyStatusList.get(mobyStatusList.size() - 1);
        assertThat(testMobyStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMobyStatus.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMobyStatus() throws Exception {
        int databaseSizeBeforeUpdate = mobyStatusRepository.findAll().size();

        // Create the MobyStatus
        MobyStatusDTO mobyStatusDTO = mobyStatusMapper.toDto(mobyStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMobyStatusMockMvc.perform(put("/api/moby-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mobyStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MobyStatus in the database
        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMobyStatus() throws Exception {
        // Initialize the database
        mobyStatusRepository.saveAndFlush(mobyStatus);

        int databaseSizeBeforeDelete = mobyStatusRepository.findAll().size();

        // Delete the mobyStatus
        restMobyStatusMockMvc.perform(delete("/api/moby-statuses/{id}", mobyStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MobyStatus> mobyStatusList = mobyStatusRepository.findAll();
        assertThat(mobyStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
