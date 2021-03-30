package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.ProjectphaseActivity;
import com.emoby.mph.domain.Projectphase;
import com.emoby.mph.domain.Activity;
import com.emoby.mph.repository.ProjectphaseActivityRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.ProjectphaseActivityService;
import com.emoby.mph.service.dto.ProjectphaseActivityDTO;
import com.emoby.mph.service.mapper.ProjectphaseActivityMapper;

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
 * Integration tests for the {@link ProjectphaseActivityResource} REST
 * controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class ProjectphaseActivityResourceIT {

	@Autowired
	private ProjectphaseActivityRepository projectphaseActivityRepository;

	@Autowired
	private ProjectphaseActivityMapper projectphaseActivityMapper;

	@Autowired
	private ProjectphaseActivityService projectphaseActivityService;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restProjectphaseActivityMockMvc;

	private ProjectphaseActivity projectphaseActivity;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static ProjectphaseActivity createEntity(EntityManager em) {
		ProjectphaseActivity projectphaseActivity = new ProjectphaseActivity();
		// Add required entity
		Projectphase projectphase;
		projectphase = ProjectphaseResourceIT.createEntity(em);
		em.persist(projectphase);
		em.flush();
		projectphaseActivity.setProjectphase(projectphase);
		// Add required entity
		Activity activity;
		activity = ActivityResourceIT.createEntity(em);
		em.persist(activity);
		em.flush();
		projectphaseActivity.setActivity(activity);
		return projectphaseActivity;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static ProjectphaseActivity createUpdatedEntity(EntityManager em) {
		ProjectphaseActivity projectphaseActivity = new ProjectphaseActivity();
		// Add required entity
		Projectphase projectphase;
		projectphase = ProjectphaseResourceIT.createUpdatedEntity(em);
		em.persist(projectphase);
		em.flush();
		projectphaseActivity.setProjectphase(projectphase);
		// Add required entity
		Activity activity;
		activity = ActivityResourceIT.createUpdatedEntity(em);
		em.persist(activity);
		em.flush();
		projectphaseActivity.setActivity(activity);
		return projectphaseActivity;
	}

	@BeforeEach
	public void initTest() {
		projectphaseActivity = createEntity(em);
	}

	@Test
	@Transactional
	public void createProjectphaseActivity() throws Exception {
		int databaseSizeBeforeCreate = projectphaseActivityRepository.findAll().size();
		// Create the ProjectphaseActivity
		ProjectphaseActivityDTO projectphaseActivityDTO = projectphaseActivityMapper.toDto(projectphaseActivity);
		restProjectphaseActivityMockMvc
				.perform(post("/api/projectphase-activities").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(projectphaseActivityDTO)))
				.andExpect(status().isCreated());

		// Validate the ProjectphaseActivity in the database
		List<ProjectphaseActivity> projectphaseActivityList = projectphaseActivityRepository.findAll();
		assertThat(projectphaseActivityList).hasSize(databaseSizeBeforeCreate + 1);
		ProjectphaseActivity testProjectphaseActivity = projectphaseActivityList
				.get(projectphaseActivityList.size() - 1);
	}

	@Test
	@Transactional
	public void createProjectphaseActivityWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = projectphaseActivityRepository.findAll().size();

		// Create the ProjectphaseActivity with an existing ID
		projectphaseActivity.setId(1L);
		ProjectphaseActivityDTO projectphaseActivityDTO = projectphaseActivityMapper.toDto(projectphaseActivity);

		// An entity with an existing ID cannot be created, so this API call must fail
		restProjectphaseActivityMockMvc
				.perform(post("/api/projectphase-activities").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(projectphaseActivityDTO)))
				.andExpect(status().isBadRequest());

		// Validate the ProjectphaseActivity in the database
		List<ProjectphaseActivity> projectphaseActivityList = projectphaseActivityRepository.findAll();
		assertThat(projectphaseActivityList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllProjectphaseActivities() throws Exception {
		// Initialize the database
		projectphaseActivityRepository.saveAndFlush(projectphaseActivity);

		// Get all the projectphaseActivityList
		restProjectphaseActivityMockMvc.perform(get("/api/projectphase-activities?sort=id,desc"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(projectphaseActivity.getId().intValue())));
	}

	@Test
	@Transactional
	public void getProjectphaseActivity() throws Exception {
		// Initialize the database
		projectphaseActivityRepository.saveAndFlush(projectphaseActivity);

		// Get the projectphaseActivity
		restProjectphaseActivityMockMvc.perform(get("/api/projectphase-activities/{id}", projectphaseActivity.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(projectphaseActivity.getId().intValue()));
	}

	@Test
	@Transactional
	public void getNonExistingProjectphaseActivity() throws Exception {
		// Get the projectphaseActivity
		restProjectphaseActivityMockMvc.perform(get("/api/projectphase-activities/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateProjectphaseActivity() throws Exception {
		// Initialize the database
		projectphaseActivityRepository.saveAndFlush(projectphaseActivity);

		int databaseSizeBeforeUpdate = projectphaseActivityRepository.findAll().size();

		// Update the projectphaseActivity
		ProjectphaseActivity updatedProjectphaseActivity = projectphaseActivityRepository
				.findById(projectphaseActivity.getId()).get();
		// Disconnect from session so that the updates on updatedProjectphaseActivity
		// are not directly saved in db
		em.detach(updatedProjectphaseActivity);
		ProjectphaseActivityDTO projectphaseActivityDTO = projectphaseActivityMapper.toDto(updatedProjectphaseActivity);

		restProjectphaseActivityMockMvc
				.perform(put("/api/projectphase-activities").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(projectphaseActivityDTO)))
				.andExpect(status().isOk());

		// Validate the ProjectphaseActivity in the database
		List<ProjectphaseActivity> projectphaseActivityList = projectphaseActivityRepository.findAll();
		assertThat(projectphaseActivityList).hasSize(databaseSizeBeforeUpdate);
		ProjectphaseActivity testProjectphaseActivity = projectphaseActivityList
				.get(projectphaseActivityList.size() - 1);
	}

	@Test
	@Transactional
	public void updateNonExistingProjectphaseActivity() throws Exception {
		int databaseSizeBeforeUpdate = projectphaseActivityRepository.findAll().size();

		// Create the ProjectphaseActivity
		ProjectphaseActivityDTO projectphaseActivityDTO = projectphaseActivityMapper.toDto(projectphaseActivity);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restProjectphaseActivityMockMvc
				.perform(put("/api/projectphase-activities").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(projectphaseActivityDTO)))
				.andExpect(status().isBadRequest());

		// Validate the ProjectphaseActivity in the database
		List<ProjectphaseActivity> projectphaseActivityList = projectphaseActivityRepository.findAll();
		assertThat(projectphaseActivityList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteProjectphaseActivity() throws Exception {
		// Initialize the database
		projectphaseActivityRepository.saveAndFlush(projectphaseActivity);

		int databaseSizeBeforeDelete = projectphaseActivityRepository.findAll().size();

		// Delete the projectphaseActivity
		restProjectphaseActivityMockMvc
				.perform(delete("/api/projectphase-activities/{id}", projectphaseActivity.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<ProjectphaseActivity> projectphaseActivityList = projectphaseActivityRepository.findAll();
		assertThat(projectphaseActivityList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
