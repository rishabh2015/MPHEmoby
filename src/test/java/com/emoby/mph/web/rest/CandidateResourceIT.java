package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Candidate;
import com.emoby.mph.domain.Country;
import com.emoby.mph.domain.Educationlevel;
import com.emoby.mph.domain.Experience;
import com.emoby.mph.repository.CandidateRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.CandidateService;
import com.emoby.mph.service.dto.CandidateDTO;
import com.emoby.mph.service.mapper.CandidateMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.emoby.mph.domain.enumeration.Gender;
/**
 * Integration tests for the {@link CandidateResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.FORBIDDEN)
public class CandidateResourceIT {

    private static final Gender DEFAULT_GENDER = Gender.F;
    private static final Gender UPDATED_GENDER = Gender.M;

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TEXT_CLEAN = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_CLEAN = "BBBBBBBBBB";

    private static final UUID DEFAULT_GUID = UUID.randomUUID();
    private static final UUID UPDATED_GUID = UUID.randomUUID();

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateRepository candidateRepositoryMock;

    @Autowired
    private CandidateMapper candidateMapper;

    @Mock
    private CandidateService candidateServiceMock;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCandidateMockMvc;

    private Candidate candidate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .gender(DEFAULT_GENDER)
            .last_name(DEFAULT_LAST_NAME)
            .first_name(DEFAULT_FIRST_NAME)
            .date_of_birth(DEFAULT_DATE_OF_BIRTH)
            .text_clean(DEFAULT_TEXT_CLEAN)
            .guid(DEFAULT_GUID)
            .creation_date(DEFAULT_CREATION_DATE)
            .update_date(DEFAULT_UPDATE_DATE)
            .comment(DEFAULT_COMMENT)
            .email(DEFAULT_EMAIL);
        // Add required entity
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            country = CountryResourceIT.createEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        candidate.setNationality(country);
        // Add required entity
        candidate.setLocation(country);
        // Add required entity
        Educationlevel educationlevel;
        if (TestUtil.findAll(em, Educationlevel.class).isEmpty()) {
            educationlevel = EducationlevelResourceIT.createEntity(em);
            em.persist(educationlevel);
            em.flush();
        } else {
            educationlevel = TestUtil.findAll(em, Educationlevel.class).get(0);
        }
        candidate.setEducationlevel(educationlevel);
        // Add required entity
        Experience experience;
        if (TestUtil.findAll(em, Experience.class).isEmpty()) {
            experience = ExperienceResourceIT.createEntity(em);
            em.persist(experience);
            em.flush();
        } else {
            experience = TestUtil.findAll(em, Experience.class).get(0);
        }
        candidate.setExperience(experience);
        return candidate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Candidate createUpdatedEntity(EntityManager em) {
        Candidate candidate = new Candidate()
            .gender(UPDATED_GENDER)
            .last_name(UPDATED_LAST_NAME)
            .first_name(UPDATED_FIRST_NAME)
            .date_of_birth(UPDATED_DATE_OF_BIRTH)
            .text_clean(UPDATED_TEXT_CLEAN)
            .guid(UPDATED_GUID)
            .creation_date(UPDATED_CREATION_DATE)
            .update_date(UPDATED_UPDATE_DATE)
            .comment(UPDATED_COMMENT)
            .email(UPDATED_EMAIL);
        // Add required entity
        Country country;
        if (TestUtil.findAll(em, Country.class).isEmpty()) {
            country = CountryResourceIT.createUpdatedEntity(em);
            em.persist(country);
            em.flush();
        } else {
            country = TestUtil.findAll(em, Country.class).get(0);
        }
        candidate.setNationality(country);
        // Add required entity
        candidate.setLocation(country);
        // Add required entity
        Educationlevel educationlevel;
        if (TestUtil.findAll(em, Educationlevel.class).isEmpty()) {
            educationlevel = EducationlevelResourceIT.createUpdatedEntity(em);
            em.persist(educationlevel);
            em.flush();
        } else {
            educationlevel = TestUtil.findAll(em, Educationlevel.class).get(0);
        }
        candidate.setEducationlevel(educationlevel);
        // Add required entity
        Experience experience;
        if (TestUtil.findAll(em, Experience.class).isEmpty()) {
            experience = ExperienceResourceIT.createUpdatedEntity(em);
            em.persist(experience);
            em.flush();
        } else {
            experience = TestUtil.findAll(em, Experience.class).get(0);
        }
        candidate.setExperience(experience);
        return candidate;
    }

    @BeforeEach
    public void initTest() {
        candidate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidate() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();
        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);
        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isCreated());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate + 1);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCandidate.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCandidate.getFirst_name()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCandidate.getDate_of_birth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCandidate.getText_clean()).isEqualTo(DEFAULT_TEXT_CLEAN);
        assertThat(testCandidate.getGuid()).isEqualTo(DEFAULT_GUID);
        assertThat(testCandidate.getCreation_date()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCandidate.getUpdate_date()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testCandidate.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testCandidate.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createCandidateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateRepository.findAll().size();

        // Create the Candidate with an existing ID
        candidate.setId(1L);
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setGender(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setLast_name(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirst_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setFirst_name(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_of_birthIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setDate_of_birth(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateRepository.findAll().size();
        // set the field null
        candidate.setGuid(null);

        // Create the Candidate, which fails.
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);


        restCandidateMockMvc.perform(post("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidates() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get all the candidateList
        restCandidateMockMvc.perform(get("/api/candidates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidate.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].first_name").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].date_of_birth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].text_clean").value(hasItem(DEFAULT_TEXT_CLEAN.toString())))
            .andExpect(jsonPath("$.[*].guid").value(hasItem(DEFAULT_GUID.toString())))
            .andExpect(jsonPath("$.[*].creation_date").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].update_date").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCandidatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(candidateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidateMockMvc.perform(get("/api/candidates?eagerload=true"))
            .andExpect(status().isOk());

        verify(candidateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCandidatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(candidateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCandidateMockMvc.perform(get("/api/candidates?eagerload=true"))
            .andExpect(status().isOk());

        verify(candidateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", candidate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(candidate.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.first_name").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.date_of_birth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.text_clean").value(DEFAULT_TEXT_CLEAN.toString()))
            .andExpect(jsonPath("$.guid").value(DEFAULT_GUID.toString()))
            .andExpect(jsonPath("$.creation_date").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.update_date").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingCandidate() throws Exception {
        // Get the candidate
        restCandidateMockMvc.perform(get("/api/candidates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Update the candidate
        Candidate updatedCandidate = candidateRepository.findById(candidate.getId()).get();
        // Disconnect from session so that the updates on updatedCandidate are not directly saved in db
        em.detach(updatedCandidate);
        updatedCandidate
            .gender(UPDATED_GENDER)
            .last_name(UPDATED_LAST_NAME)
            .first_name(UPDATED_FIRST_NAME)
            .date_of_birth(UPDATED_DATE_OF_BIRTH)
            .text_clean(UPDATED_TEXT_CLEAN)
            .guid(UPDATED_GUID)
            .creation_date(UPDATED_CREATION_DATE)
            .update_date(UPDATED_UPDATE_DATE)
            .comment(UPDATED_COMMENT)
            .email(UPDATED_EMAIL);
        CandidateDTO candidateDTO = candidateMapper.toDto(updatedCandidate);

        restCandidateMockMvc.perform(put("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isOk());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
        Candidate testCandidate = candidateList.get(candidateList.size() - 1);
        assertThat(testCandidate.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCandidate.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCandidate.getFirst_name()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCandidate.getDate_of_birth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCandidate.getText_clean()).isEqualTo(UPDATED_TEXT_CLEAN);
        assertThat(testCandidate.getGuid()).isEqualTo(UPDATED_GUID);
        assertThat(testCandidate.getCreation_date()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCandidate.getUpdate_date()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testCandidate.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testCandidate.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidate() throws Exception {
        int databaseSizeBeforeUpdate = candidateRepository.findAll().size();

        // Create the Candidate
        CandidateDTO candidateDTO = candidateMapper.toDto(candidate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCandidateMockMvc.perform(put("/api/candidates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(candidateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Candidate in the database
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCandidate() throws Exception {
        // Initialize the database
        candidateRepository.saveAndFlush(candidate);

        int databaseSizeBeforeDelete = candidateRepository.findAll().size();

        // Delete the candidate
        restCandidateMockMvc.perform(delete("/api/candidates/{id}", candidate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Candidate> candidateList = candidateRepository.findAll();
        assertThat(candidateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
