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

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

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
import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.repository.JobOpeningRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.JobOpeningService;
import com.emoby.mph.service.dto.JobOpeningDTO;
import com.emoby.mph.service.mapper.JobOpeningMapper;

/**
 * Integration tests for the {@link JobOpeningResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class JobOpeningResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final UUID DEFAULT_GUID = UUID.randomUUID();
    private static final UUID UPDATED_GUID = UUID.randomUUID();

    private static final String DEFAULT_TEXT_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_CLEAN = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private JobOpeningRepository jobOpeningRepository;

    @Autowired
    private JobOpeningMapper jobOpeningMapper;

    @Autowired
    private JobOpeningService jobOpeningService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobOpeningMockMvc;

    private JobOpening jobOpening;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobOpening createEntity(EntityManager em) {
        JobOpening jobOpening = new JobOpening()
            .title(DEFAULT_TITLE)
            .guid(DEFAULT_GUID)
            .text_clean(DEFAULT_TEXT_CLEAN)
            .creation_date(DEFAULT_CREATION_DATE)
            .delete_date(DEFAULT_DELETE_DATE);
        return jobOpening;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobOpening createUpdatedEntity(EntityManager em) {
        JobOpening jobOpening = new JobOpening()
            .title(UPDATED_TITLE)
            .guid(UPDATED_GUID)
            .text_clean(UPDATED_TEXT_CLEAN)
            .creation_date(UPDATED_CREATION_DATE)
            .delete_date(UPDATED_DELETE_DATE);
        return jobOpening;
    }

    @BeforeEach
    public void initTest() {
        jobOpening = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobOpening() throws Exception {
        int databaseSizeBeforeCreate = jobOpeningRepository.findAll().size();
        // Create the JobOpening
        JobOpeningDTO jobOpeningDTO = jobOpeningMapper.toDto(jobOpening);
        restJobOpeningMockMvc.perform(post("/api/job-openings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobOpeningDTO)))
            .andExpect(status().isCreated());

        // Validate the JobOpening in the database
        List<JobOpening> jobOpeningList = jobOpeningRepository.findAll();
        assertThat(jobOpeningList).hasSize(databaseSizeBeforeCreate + 1);
        JobOpening testJobOpening = jobOpeningList.get(jobOpeningList.size() - 1);
        assertThat(testJobOpening.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobOpening.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testJobOpening.getText_clean()).isEqualTo(DEFAULT_TEXT_CLEAN);
        assertThat(testJobOpening.getCreation_date()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testJobOpening.getDelete_date()).isEqualTo(DEFAULT_DELETE_DATE);
    }

    @Test
    @Transactional
    public void createJobOpeningWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobOpeningRepository.findAll().size();

        // Create the JobOpening with an existing ID
        jobOpening.setId(1L);
        JobOpeningDTO jobOpeningDTO = jobOpeningMapper.toDto(jobOpening);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOpeningMockMvc.perform(post("/api/job-openings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobOpeningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobOpening in the database
        List<JobOpening> jobOpeningList = jobOpeningRepository.findAll();
        assertThat(jobOpeningList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOpeningRepository.findAll().size();
        // set the field null
        jobOpening.setTitle(null);

        // Create the JobOpening, which fails.
        JobOpeningDTO jobOpeningDTO = jobOpeningMapper.toDto(jobOpening);


        restJobOpeningMockMvc.perform(post("/api/job-openings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobOpeningDTO)))
            .andExpect(status().isBadRequest());

        List<JobOpening> jobOpeningList = jobOpeningRepository.findAll();
        assertThat(jobOpeningList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobOpenings() throws Exception {
        // Initialize the database
        jobOpeningRepository.saveAndFlush(jobOpening);

        // Get all the jobOpeningList
        restJobOpeningMockMvc.perform(get("/api/job-openings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOpening.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].text_clean").value(hasItem(DEFAULT_TEXT_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].delete_date").value(hasItem(DEFAULT_DELETE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getJobOpening() throws Exception {
        // Initialize the database
        jobOpeningRepository.saveAndFlush(jobOpening);

        // Get the jobOpening
        restJobOpeningMockMvc.perform(get("/api/job-openings/{id}", jobOpening.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobOpening.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.text_clean").value(DEFAULT_TEXT_CLEAN.toString()))
            .andExpect(jsonPath("$.creation_date").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.delete_date").value(DEFAULT_DELETE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingJobOpening() throws Exception {
        // Get the jobOpening
        restJobOpeningMockMvc.perform(get("/api/job-openings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobOpening() throws Exception {
        // Initialize the database
        jobOpeningRepository.saveAndFlush(jobOpening);

        int databaseSizeBeforeUpdate = jobOpeningRepository.findAll().size();

        // Update the jobOpening
        JobOpening updatedJobOpening = jobOpeningRepository.findById(jobOpening.getId()).get();
        // Disconnect from session so that the updates on updatedJobOpening are not directly saved in db
        em.detach(updatedJobOpening);
        updatedJobOpening
            .title(UPDATED_TITLE)
            .guid(UPDATED_GUID)
            .text_clean(UPDATED_TEXT_CLEAN)
            .creation_date(UPDATED_CREATION_DATE)
            .delete_date(UPDATED_DELETE_DATE);
        JobOpeningDTO jobOpeningDTO = jobOpeningMapper.toDto(updatedJobOpening);

        restJobOpeningMockMvc.perform(put("/api/job-openings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobOpeningDTO)))
            .andExpect(status().isOk());

        // Validate the JobOpening in the database
        List<JobOpening> jobOpeningList = jobOpeningRepository.findAll();
        assertThat(jobOpeningList).hasSize(databaseSizeBeforeUpdate);
        JobOpening testJobOpening = jobOpeningList.get(jobOpeningList.size() - 1);
        assertThat(testJobOpening.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobOpening.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testJobOpening.getText_clean()).isEqualTo(UPDATED_TEXT_CLEAN);
        assertThat(testJobOpening.getCreation_date()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testJobOpening.getDelete_date()).isEqualTo(UPDATED_DELETE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingJobOpening() throws Exception {
        int databaseSizeBeforeUpdate = jobOpeningRepository.findAll().size();

        // Create the JobOpening
        JobOpeningDTO jobOpeningDTO = jobOpeningMapper.toDto(jobOpening);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobOpeningMockMvc.perform(put("/api/job-openings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobOpeningDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobOpening in the database
        List<JobOpening> jobOpeningList = jobOpeningRepository.findAll();
        assertThat(jobOpeningList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobOpening() throws Exception {
        // Initialize the database
        jobOpeningRepository.saveAndFlush(jobOpening);

        int databaseSizeBeforeDelete = jobOpeningRepository.findAll().size();

        // Delete the jobOpening
        restJobOpeningMockMvc.perform(delete("/api/job-openings/{id}", jobOpening.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobOpening> jobOpeningList = jobOpeningRepository.findAll();
        assertThat(jobOpeningList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
