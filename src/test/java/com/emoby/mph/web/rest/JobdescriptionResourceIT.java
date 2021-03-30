package com.emoby.mph.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Content;
import com.emoby.mph.domain.Jobdescription;
import com.emoby.mph.domain.Project;
import com.emoby.mph.domain.enumeration.Gender;
import com.emoby.mph.repository.JobdescriptionRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.JobdescriptionService;
import com.emoby.mph.service.dto.JobdescriptionDTO;
import com.emoby.mph.service.mapper.JobdescriptionMapper;
/**
 * Integration tests for the {@link JobdescriptionResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class JobdescriptionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Gender DEFAULT_GENDER = Gender.M;
    private static final Gender UPDATED_GENDER = Gender.F;

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    @Autowired
    private JobdescriptionRepository jobdescriptionRepository;

    @Mock
    private JobdescriptionRepository jobdescriptionRepositoryMock;

    @Autowired
    private JobdescriptionMapper jobdescriptionMapper;

    @Mock
    private JobdescriptionService jobdescriptionServiceMock;

    @Autowired
    private JobdescriptionService jobdescriptionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobdescriptionMockMvc;

    private Jobdescription jobdescription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jobdescription createEntity(EntityManager em) {
        Jobdescription jobdescription = new Jobdescription()
            .name(DEFAULT_NAME)
            .creation_dt(DEFAULT_CREATION_DT)
            .gender(DEFAULT_GENDER)
            .filename(DEFAULT_FILENAME);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        jobdescription.setProject(project);
        // Add required entity
        Content content;
        if (TestUtil.findAll(em, Content.class).isEmpty()) {
            content = ContentResourceIT.createEntity(em);
            em.persist(content);
            em.flush();
        } else {
            content = TestUtil.findAll(em, Content.class).get(0);
        }
        jobdescription.setContent(content);
        return jobdescription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jobdescription createUpdatedEntity(EntityManager em) {
        Jobdescription jobdescription = new Jobdescription()
            .name(UPDATED_NAME)
            .creation_dt(UPDATED_CREATION_DT)
            .gender(UPDATED_GENDER)
            .filename(UPDATED_FILENAME);
        // Add required entity
        Project project;
        if (TestUtil.findAll(em, Project.class).isEmpty()) {
            project = ProjectResourceIT.createUpdatedEntity(em);
            em.persist(project);
            em.flush();
        } else {
            project = TestUtil.findAll(em, Project.class).get(0);
        }
        jobdescription.setProject(project);
        // Add required entity
        Content content;
        if (TestUtil.findAll(em, Content.class).isEmpty()) {
            content = ContentResourceIT.createUpdatedEntity(em);
            em.persist(content);
            em.flush();
        } else {
            content = TestUtil.findAll(em, Content.class).get(0);
        }
        jobdescription.setContent(content);
        return jobdescription;
    }

    @BeforeEach
    public void initTest() {
        jobdescription = createEntity(em);
    }

    @Transactional
    public void createJobdescription() throws Exception {
        int databaseSizeBeforeCreate = jobdescriptionRepository.findAll().size();
        // Create the Jobdescription
        jobdescription.setContentId(new Long(1));
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(jobdescription);
        restJobdescriptionMockMvc.perform(post("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the Jobdescription in the database
        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Jobdescription testJobdescription = jobdescriptionList.get(jobdescriptionList.size() - 1);
        assertThat(testJobdescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobdescription.getCreation_dt()).isEqualTo(DEFAULT_CREATION_DT);
        assertThat(testJobdescription.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testJobdescription.getFilename()).isEqualTo(DEFAULT_FILENAME);
    }

    @Test
    @Transactional
    public void createJobdescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobdescriptionRepository.findAll().size();

        // Create the Jobdescription with an existing ID
        jobdescription.setId(1L);
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(jobdescription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobdescriptionMockMvc.perform(post("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jobdescription in the database
        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobdescriptionRepository.findAll().size();
        // set the field null
        jobdescription.setName(null);

        // Create the Jobdescription, which fails.
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(jobdescription);


        restJobdescriptionMockMvc.perform(post("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isBadRequest());

        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreation_dtIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobdescriptionRepository.findAll().size();
        // set the field null
        jobdescription.setCreation_dt(null);

        // Create the Jobdescription, which fails.
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(jobdescription);


        restJobdescriptionMockMvc.perform(post("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isBadRequest());

        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobdescriptionRepository.findAll().size();
        // set the field null
        jobdescription.setFilename(null);

        // Create the Jobdescription, which fails.
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(jobdescription);


        restJobdescriptionMockMvc.perform(post("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isBadRequest());

        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobdescriptions() throws Exception {
        // Initialize the database
        jobdescriptionRepository.saveAndFlush(jobdescription);

        // Get all the jobdescriptionList
        restJobdescriptionMockMvc.perform(get("/api/jobdescriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobdescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].creation_dt").value(hasItem(DEFAULT_CREATION_DT.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllJobdescriptionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(jobdescriptionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobdescriptionMockMvc.perform(get("/api/jobdescriptions?eagerload=true"))
            .andExpect(status().isOk());

        verify(jobdescriptionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllJobdescriptionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(jobdescriptionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobdescriptionMockMvc.perform(get("/api/jobdescriptions?eagerload=true"))
            .andExpect(status().isOk());

        verify(jobdescriptionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getJobdescription() throws Exception {
        // Initialize the database
        jobdescriptionRepository.saveAndFlush(jobdescription);

        // Get the jobdescription
        restJobdescriptionMockMvc.perform(get("/api/jobdescriptions/{id}", jobdescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobdescription.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.creation_dt").value(DEFAULT_CREATION_DT.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME));
    }
    @Test
    @Transactional
    public void getNonExistingJobdescription() throws Exception {
        // Get the jobdescription
        restJobdescriptionMockMvc.perform(get("/api/jobdescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Transactional
    public void updateJobdescription() throws Exception {
        // Initialize the database
        jobdescriptionRepository.saveAndFlush(jobdescription);

        int databaseSizeBeforeUpdate = jobdescriptionRepository.findAll().size();

        // Update the jobdescription
        Jobdescription updatedJobdescription = jobdescriptionRepository.findById(jobdescription.getId()).get();
        // Disconnect from session so that the updates on updatedJobdescription are not directly saved in db
        em.detach(updatedJobdescription);
        updatedJobdescription
            .name(UPDATED_NAME)
            .creation_dt(UPDATED_CREATION_DT)
            .gender(UPDATED_GENDER)
            .filename(UPDATED_FILENAME);
        updatedJobdescription.setContentId(new Long(1));
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(updatedJobdescription);

        restJobdescriptionMockMvc.perform(put("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isOk());

        // Validate the Jobdescription in the database
        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeUpdate);
        Jobdescription testJobdescription = jobdescriptionList.get(jobdescriptionList.size() - 1);
        assertThat(testJobdescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobdescription.getCreation_dt()).isEqualTo(UPDATED_CREATION_DT);
        assertThat(testJobdescription.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testJobdescription.getFilename()).isEqualTo(UPDATED_FILENAME);
    }

    @Test
    @Transactional
    public void updateNonExistingJobdescription() throws Exception {
        int databaseSizeBeforeUpdate = jobdescriptionRepository.findAll().size();

        // Create the Jobdescription
        JobdescriptionDTO jobdescriptionDTO = jobdescriptionMapper.toDto(jobdescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobdescriptionMockMvc.perform(put("/api/jobdescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jobdescriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Jobdescription in the database
        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobdescription() throws Exception {
        // Initialize the database
        jobdescriptionRepository.saveAndFlush(jobdescription);

        int databaseSizeBeforeDelete = jobdescriptionRepository.findAll().size();

        // Delete the jobdescription
        restJobdescriptionMockMvc.perform(delete("/api/jobdescriptions/{id}", jobdescription.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jobdescription> jobdescriptionList = jobdescriptionRepository.findAll();
        assertThat(jobdescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
