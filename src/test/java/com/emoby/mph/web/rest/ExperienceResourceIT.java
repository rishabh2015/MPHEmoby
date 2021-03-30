package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Experience;
import com.emoby.mph.repository.ExperienceRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.ExperienceService;
import com.emoby.mph.service.dto.ExperienceDTO;
import com.emoby.mph.service.mapper.ExperienceMapper;

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
 * Integration tests for the {@link ExperienceResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class ExperienceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExperienceMockMvc;

    private Experience experience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createEntity(EntityManager em) {
        Experience experience = new Experience()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return experience;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Experience createUpdatedEntity(EntityManager em) {
        Experience experience = new Experience()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return experience;
    }

    @BeforeEach
    public void initTest() {
        experience = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperience() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();
        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isCreated());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate + 1);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testExperience.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createExperienceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceRepository.findAll().size();

        // Create the Experience with an existing ID
        experience.setId(1L);
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setCode(null);

        // Create the Experience, which fails.
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);


        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceRepository.findAll().size();
        // set the field null
        experience.setLibelle(null);

        // Create the Experience, which fails.
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);


        restExperienceMockMvc.perform(post("/api/experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperiences() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get all the experienceList
        restExperienceMockMvc.perform(get("/api/experiences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experience.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", experience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(experience.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingExperience() throws Exception {
        // Get the experience
        restExperienceMockMvc.perform(get("/api/experiences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Update the experience
        Experience updatedExperience = experienceRepository.findById(experience.getId()).get();
        // Disconnect from session so that the updates on updatedExperience are not directly saved in db
        em.detach(updatedExperience);
        updatedExperience
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        ExperienceDTO experienceDTO = experienceMapper.toDto(updatedExperience);

        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isOk());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
        Experience testExperience = experienceList.get(experienceList.size() - 1);
        assertThat(testExperience.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testExperience.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingExperience() throws Exception {
        int databaseSizeBeforeUpdate = experienceRepository.findAll().size();

        // Create the Experience
        ExperienceDTO experienceDTO = experienceMapper.toDto(experience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExperienceMockMvc.perform(put("/api/experiences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(experienceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Experience in the database
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExperience() throws Exception {
        // Initialize the database
        experienceRepository.saveAndFlush(experience);

        int databaseSizeBeforeDelete = experienceRepository.findAll().size();

        // Delete the experience
        restExperienceMockMvc.perform(delete("/api/experiences/{id}", experience.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Experience> experienceList = experienceRepository.findAll();
        assertThat(experienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
