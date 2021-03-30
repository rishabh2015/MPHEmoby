package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Projectphase;
import com.emoby.mph.repository.ProjectphaseRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.ProjectphaseService;
import com.emoby.mph.service.dto.ProjectphaseDTO;
import com.emoby.mph.service.mapper.ProjectphaseMapper;

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
 * Integration tests for the {@link ProjectphaseResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class ProjectphaseResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ProjectphaseRepository projectphaseRepository;

    @Autowired
    private ProjectphaseMapper projectphaseMapper;

    @Autowired
    private ProjectphaseService projectphaseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectphaseMockMvc;

    private Projectphase projectphase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectphase createEntity(EntityManager em) {
        Projectphase projectphase = new Projectphase()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return projectphase;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projectphase createUpdatedEntity(EntityManager em) {
        Projectphase projectphase = new Projectphase()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return projectphase;
    }

    @BeforeEach
    public void initTest() {
        projectphase = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectphase() throws Exception {
        int databaseSizeBeforeCreate = projectphaseRepository.findAll().size();
        // Create the Projectphase
        ProjectphaseDTO projectphaseDTO = projectphaseMapper.toDto(projectphase);
        restProjectphaseMockMvc.perform(post("/api/projectphases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectphaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Projectphase in the database
        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeCreate + 1);
        Projectphase testProjectphase = projectphaseList.get(projectphaseList.size() - 1);
        assertThat(testProjectphase.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProjectphase.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createProjectphaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectphaseRepository.findAll().size();

        // Create the Projectphase with an existing ID
        projectphase.setId(1L);
        ProjectphaseDTO projectphaseDTO = projectphaseMapper.toDto(projectphase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectphaseMockMvc.perform(post("/api/projectphases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectphaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projectphase in the database
        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectphaseRepository.findAll().size();
        // set the field null
        projectphase.setCode(null);

        // Create the Projectphase, which fails.
        ProjectphaseDTO projectphaseDTO = projectphaseMapper.toDto(projectphase);


        restProjectphaseMockMvc.perform(post("/api/projectphases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectphaseDTO)))
            .andExpect(status().isBadRequest());

        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectphaseRepository.findAll().size();
        // set the field null
        projectphase.setLibelle(null);

        // Create the Projectphase, which fails.
        ProjectphaseDTO projectphaseDTO = projectphaseMapper.toDto(projectphase);


        restProjectphaseMockMvc.perform(post("/api/projectphases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectphaseDTO)))
            .andExpect(status().isBadRequest());

        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectphases() throws Exception {
        // Initialize the database
        projectphaseRepository.saveAndFlush(projectphase);

        // Get all the projectphaseList
        restProjectphaseMockMvc.perform(get("/api/projectphases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectphase.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getProjectphase() throws Exception {
        // Initialize the database
        projectphaseRepository.saveAndFlush(projectphase);

        // Get the projectphase
        restProjectphaseMockMvc.perform(get("/api/projectphases/{id}", projectphase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectphase.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingProjectphase() throws Exception {
        // Get the projectphase
        restProjectphaseMockMvc.perform(get("/api/projectphases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectphase() throws Exception {
        // Initialize the database
        projectphaseRepository.saveAndFlush(projectphase);

        int databaseSizeBeforeUpdate = projectphaseRepository.findAll().size();

        // Update the projectphase
        Projectphase updatedProjectphase = projectphaseRepository.findById(projectphase.getId()).get();
        // Disconnect from session so that the updates on updatedProjectphase are not directly saved in db
        em.detach(updatedProjectphase);
        updatedProjectphase
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        ProjectphaseDTO projectphaseDTO = projectphaseMapper.toDto(updatedProjectphase);

        restProjectphaseMockMvc.perform(put("/api/projectphases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectphaseDTO)))
            .andExpect(status().isOk());

        // Validate the Projectphase in the database
        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeUpdate);
        Projectphase testProjectphase = projectphaseList.get(projectphaseList.size() - 1);
        assertThat(testProjectphase.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProjectphase.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectphase() throws Exception {
        int databaseSizeBeforeUpdate = projectphaseRepository.findAll().size();

        // Create the Projectphase
        ProjectphaseDTO projectphaseDTO = projectphaseMapper.toDto(projectphase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectphaseMockMvc.perform(put("/api/projectphases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(projectphaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Projectphase in the database
        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjectphase() throws Exception {
        // Initialize the database
        projectphaseRepository.saveAndFlush(projectphase);

        int databaseSizeBeforeDelete = projectphaseRepository.findAll().size();

        // Delete the projectphase
        restProjectphaseMockMvc.perform(delete("/api/projectphases/{id}", projectphase.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projectphase> projectphaseList = projectphaseRepository.findAll();
        assertThat(projectphaseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
