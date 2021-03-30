package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.Educationlevel;
import com.emoby.mph.repository.EducationlevelRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.EducationlevelService;
import com.emoby.mph.service.dto.EducationlevelDTO;
import com.emoby.mph.service.mapper.EducationlevelMapper;

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
 * Integration tests for the {@link EducationlevelResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class EducationlevelResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private EducationlevelRepository educationlevelRepository;

    @Autowired
    private EducationlevelMapper educationlevelMapper;

    @Autowired
    private EducationlevelService educationlevelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationlevelMockMvc;

    private Educationlevel educationlevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Educationlevel createEntity(EntityManager em) {
        Educationlevel educationlevel = new Educationlevel()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return educationlevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Educationlevel createUpdatedEntity(EntityManager em) {
        Educationlevel educationlevel = new Educationlevel()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return educationlevel;
    }

    @BeforeEach
    public void initTest() {
        educationlevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationlevel() throws Exception {
        int databaseSizeBeforeCreate = educationlevelRepository.findAll().size();
        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);
        restEducationlevelMockMvc.perform(post("/api/educationlevels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO)))
            .andExpect(status().isCreated());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeCreate + 1);
        Educationlevel testEducationlevel = educationlevelList.get(educationlevelList.size() - 1);
        assertThat(testEducationlevel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEducationlevel.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createEducationlevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationlevelRepository.findAll().size();

        // Create the Educationlevel with an existing ID
        educationlevel.setId(1L);
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationlevelMockMvc.perform(post("/api/educationlevels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationlevelRepository.findAll().size();
        // set the field null
        educationlevel.setCode(null);

        // Create the Educationlevel, which fails.
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);


        restEducationlevelMockMvc.perform(post("/api/educationlevels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO)))
            .andExpect(status().isBadRequest());

        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationlevelRepository.findAll().size();
        // set the field null
        educationlevel.setLibelle(null);

        // Create the Educationlevel, which fails.
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);


        restEducationlevelMockMvc.perform(post("/api/educationlevels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO)))
            .andExpect(status().isBadRequest());

        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEducationlevels() throws Exception {
        // Initialize the database
        educationlevelRepository.saveAndFlush(educationlevel);

        // Get all the educationlevelList
        restEducationlevelMockMvc.perform(get("/api/educationlevels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationlevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getEducationlevel() throws Exception {
        // Initialize the database
        educationlevelRepository.saveAndFlush(educationlevel);

        // Get the educationlevel
        restEducationlevelMockMvc.perform(get("/api/educationlevels/{id}", educationlevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educationlevel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingEducationlevel() throws Exception {
        // Get the educationlevel
        restEducationlevelMockMvc.perform(get("/api/educationlevels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationlevel() throws Exception {
        // Initialize the database
        educationlevelRepository.saveAndFlush(educationlevel);

        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();

        // Update the educationlevel
        Educationlevel updatedEducationlevel = educationlevelRepository.findById(educationlevel.getId()).get();
        // Disconnect from session so that the updates on updatedEducationlevel are not directly saved in db
        em.detach(updatedEducationlevel);
        updatedEducationlevel
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(updatedEducationlevel);

        restEducationlevelMockMvc.perform(put("/api/educationlevels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO)))
            .andExpect(status().isOk());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
        Educationlevel testEducationlevel = educationlevelList.get(educationlevelList.size() - 1);
        assertThat(testEducationlevel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEducationlevel.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc.perform(put("/api/educationlevels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEducationlevel() throws Exception {
        // Initialize the database
        educationlevelRepository.saveAndFlush(educationlevel);

        int databaseSizeBeforeDelete = educationlevelRepository.findAll().size();

        // Delete the educationlevel
        restEducationlevelMockMvc.perform(delete("/api/educationlevels/{id}", educationlevel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
