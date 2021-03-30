package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.PotentialCandidate;
import com.emoby.mph.domain.Jobdescription;
import com.emoby.mph.domain.Candidate;
import com.emoby.mph.repository.PotentialCandidateRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.PotentialCandidateService;
import com.emoby.mph.service.dto.PotentialCandidateDTO;
import com.emoby.mph.service.mapper.PotentialCandidateMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PotentialCandidateResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class PotentialCandidateResourceIT {

    private static final Float DEFAULT_MATCHING_PERCENT = 1F;
    private static final Float UPDATED_MATCHING_PERCENT = 2F;

    private static final Instant DEFAULT_CREATION_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PotentialCandidateRepository potentialCandidateRepository;

    @Autowired
    private PotentialCandidateMapper potentialCandidateMapper;

    @Autowired
    private PotentialCandidateService potentialCandidateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPotentialCandidateMockMvc;

    private PotentialCandidate potentialCandidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotentialCandidate createEntity(EntityManager em) {
        PotentialCandidate potentialCandidate = new PotentialCandidate()
            .matching_percent(DEFAULT_MATCHING_PERCENT)
            .creation_dt(DEFAULT_CREATION_DT);
        // Add required entity
        Jobdescription jobdescription;
        if (TestUtil.findAll(em, Jobdescription.class).isEmpty()) {
            jobdescription = JobdescriptionResourceIT.createEntity(em);
            em.persist(jobdescription);
            em.flush();
        } else {
            jobdescription = TestUtil.findAll(em, Jobdescription.class).get(0);
        }
        potentialCandidate.setJobdescription(jobdescription);
        // Add required entity
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidate = CandidateResourceIT.createEntity(em);
            em.persist(candidate);
            em.flush();
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        potentialCandidate.setCandidate(candidate);
        return potentialCandidate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PotentialCandidate createUpdatedEntity(EntityManager em) {
        PotentialCandidate potentialCandidate = new PotentialCandidate()
            .matching_percent(UPDATED_MATCHING_PERCENT)
            .creation_dt(UPDATED_CREATION_DT);
        // Add required entity
        Jobdescription jobdescription;
        if (TestUtil.findAll(em, Jobdescription.class).isEmpty()) {
            jobdescription = JobdescriptionResourceIT.createUpdatedEntity(em);
            em.persist(jobdescription);
            em.flush();
        } else {
            jobdescription = TestUtil.findAll(em, Jobdescription.class).get(0);
        }
        potentialCandidate.setJobdescription(jobdescription);
        // Add required entity
        Candidate candidate;
        if (TestUtil.findAll(em, Candidate.class).isEmpty()) {
            candidate = CandidateResourceIT.createUpdatedEntity(em);
            em.persist(candidate);
            em.flush();
        } else {
            candidate = TestUtil.findAll(em, Candidate.class).get(0);
        }
        potentialCandidate.setCandidate(candidate);
        return potentialCandidate;
    }

    @BeforeEach
    public void initTest() {
        potentialCandidate = createEntity(em);
    }

    @Test
    @Transactional
    public void createPotentialCandidate() throws Exception {
        int databaseSizeBeforeCreate = potentialCandidateRepository.findAll().size();
        // Create the PotentialCandidate
        PotentialCandidateDTO potentialCandidateDTO = potentialCandidateMapper.toDto(potentialCandidate);
        restPotentialCandidateMockMvc.perform(post("/api/potential-candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialCandidateDTO)))
            .andExpect(status().isCreated());

        // Validate the PotentialCandidate in the database
        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeCreate + 1);
        PotentialCandidate testPotentialCandidate = potentialCandidateList.get(potentialCandidateList.size() - 1);
        assertThat(testPotentialCandidate.getMatching_percent()).isEqualTo(DEFAULT_MATCHING_PERCENT);
        assertThat(testPotentialCandidate.getCreation_dt()).isEqualTo(DEFAULT_CREATION_DT);
    }

    @Test
    @Transactional
    public void createPotentialCandidateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = potentialCandidateRepository.findAll().size();

        // Create the PotentialCandidate with an existing ID
        potentialCandidate.setId(1L);
        PotentialCandidateDTO potentialCandidateDTO = potentialCandidateMapper.toDto(potentialCandidate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotentialCandidateMockMvc.perform(post("/api/potential-candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialCandidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PotentialCandidate in the database
        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMatching_percentIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialCandidateRepository.findAll().size();
        // set the field null
        potentialCandidate.setMatching_percent(null);

        // Create the PotentialCandidate, which fails.
        PotentialCandidateDTO potentialCandidateDTO = potentialCandidateMapper.toDto(potentialCandidate);


        restPotentialCandidateMockMvc.perform(post("/api/potential-candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialCandidateDTO)))
            .andExpect(status().isBadRequest());

        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreation_dtIsRequired() throws Exception {
        int databaseSizeBeforeTest = potentialCandidateRepository.findAll().size();
        // set the field null
        potentialCandidate.setCreation_dt(null);

        // Create the PotentialCandidate, which fails.
        PotentialCandidateDTO potentialCandidateDTO = potentialCandidateMapper.toDto(potentialCandidate);


        restPotentialCandidateMockMvc.perform(post("/api/potential-candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialCandidateDTO)))
            .andExpect(status().isBadRequest());

        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPotentialCandidates() throws Exception {
        // Initialize the database
        potentialCandidateRepository.saveAndFlush(potentialCandidate);

        // Get all the potentialCandidateList
        restPotentialCandidateMockMvc.perform(get("/api/potential-candidates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potentialCandidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].matching_percent").value(hasItem(DEFAULT_MATCHING_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].creation_dt").value(hasItem(DEFAULT_CREATION_DT.toString())));
    }
    
    @Test
    @Transactional
    public void getPotentialCandidate() throws Exception {
        // Initialize the database
        potentialCandidateRepository.saveAndFlush(potentialCandidate);

        // Get the potentialCandidate
        restPotentialCandidateMockMvc.perform(get("/api/potential-candidates/{id}", potentialCandidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(potentialCandidate.getId().intValue()))
            .andExpect(jsonPath("$.matching_percent").value(DEFAULT_MATCHING_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.creation_dt").value(DEFAULT_CREATION_DT.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPotentialCandidate() throws Exception {
        // Get the potentialCandidate
        restPotentialCandidateMockMvc.perform(get("/api/potential-candidates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePotentialCandidate() throws Exception {
        // Initialize the database
        potentialCandidateRepository.saveAndFlush(potentialCandidate);

        int databaseSizeBeforeUpdate = potentialCandidateRepository.findAll().size();

        // Update the potentialCandidate
        PotentialCandidate updatedPotentialCandidate = potentialCandidateRepository.findById(potentialCandidate.getId()).get();
        // Disconnect from session so that the updates on updatedPotentialCandidate are not directly saved in db
        em.detach(updatedPotentialCandidate);
        updatedPotentialCandidate
            .matching_percent(UPDATED_MATCHING_PERCENT)
            .creation_dt(UPDATED_CREATION_DT);
        PotentialCandidateDTO potentialCandidateDTO = potentialCandidateMapper.toDto(updatedPotentialCandidate);

        restPotentialCandidateMockMvc.perform(put("/api/potential-candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialCandidateDTO)))
            .andExpect(status().isOk());

        // Validate the PotentialCandidate in the database
        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeUpdate);
        PotentialCandidate testPotentialCandidate = potentialCandidateList.get(potentialCandidateList.size() - 1);
        assertThat(testPotentialCandidate.getMatching_percent()).isEqualTo(UPDATED_MATCHING_PERCENT);
        assertThat(testPotentialCandidate.getCreation_dt()).isEqualTo(UPDATED_CREATION_DT);
    }

    @Test
    @Transactional
    public void updateNonExistingPotentialCandidate() throws Exception {
        int databaseSizeBeforeUpdate = potentialCandidateRepository.findAll().size();

        // Create the PotentialCandidate
        PotentialCandidateDTO potentialCandidateDTO = potentialCandidateMapper.toDto(potentialCandidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotentialCandidateMockMvc.perform(put("/api/potential-candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(potentialCandidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PotentialCandidate in the database
        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePotentialCandidate() throws Exception {
        // Initialize the database
        potentialCandidateRepository.saveAndFlush(potentialCandidate);

        int databaseSizeBeforeDelete = potentialCandidateRepository.findAll().size();

        // Delete the potentialCandidate
        restPotentialCandidateMockMvc.perform(delete("/api/potential-candidates/{id}", potentialCandidate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PotentialCandidate> potentialCandidateList = potentialCandidateRepository.findAll();
        assertThat(potentialCandidateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
