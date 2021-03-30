package com.emoby.mph.web.rest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.domain.SectorSubsector;
import com.emoby.mph.domain.Sector;
import com.emoby.mph.domain.Subsector;
import com.emoby.mph.repository.SectorsubsectorRepository;
import com.emoby.mph.security.AuthoritiesConstants;
import com.emoby.mph.service.SectorsubsectorService;
import com.emoby.mph.service.dto.SectorsubsectorDTO;
import com.emoby.mph.service.mapper.SectorsubsectorMapper;

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
 * Integration tests for the {@link SectorsubsectorResource} REST controller.
 */
@SpringBootTest(classes = EmobyMphApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
public class SectorsubsectorResourceIT {

	@Autowired
	private SectorsubsectorRepository sectorsubsectorRepository;

	@Autowired
	private SectorsubsectorMapper sectorsubsectorMapper;

	@Autowired
	private SectorsubsectorService sectorsubsectorService;

	@Autowired
	private EntityManager em;

	@Autowired
	private MockMvc restSectorsubsectorMockMvc;

	private SectorSubsector sectorsubsector;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static SectorSubsector createEntity(EntityManager em) {
		SectorSubsector sectorsubsector = new SectorSubsector();
		// Add required entity
		Sector sector;
		sector = SectorResourceIT.createUpdatedEntity(em);
		em.persist(sector);
		em.flush();

		sectorsubsector.setSector(sector);
		// Add required entity
		Subsector subsector;
		subsector = TestUtil.findAll(em, Subsector.class).get(0);

		sectorsubsector.setSubsector(subsector);
		return sectorsubsector;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static SectorSubsector createUpdatedEntity(EntityManager em) {
		SectorSubsector sectorsubsector = new SectorSubsector();
		// Add required entity
		Sector sector;
		sector = SectorResourceIT.createUpdatedEntity(em);
		em.persist(sector);
		em.flush();
		sectorsubsector.setSector(sector);
		// Add required entity
		Subsector subsector;
		subsector = SubsectorResourceIT.createUpdatedEntity(em);
		em.persist(subsector);
		em.flush();
		sectorsubsector.setSubsector(subsector);
		return sectorsubsector;
	}

	@BeforeEach
	public void initTest() {
		sectorsubsector = createEntity(em);
	}

	@Test
	@Transactional
	public void createSectorsubsector() throws Exception {
		int databaseSizeBeforeCreate = sectorsubsectorRepository.findAll().size();
		// Create the Sectorsubsector
		SectorsubsectorDTO sectorsubsectorDTO = sectorsubsectorMapper.toDto(sectorsubsector);
		restSectorsubsectorMockMvc
				.perform(post("/api/sectorsubsectors").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(sectorsubsectorDTO)))
				.andExpect(status().isCreated());

		// Validate the Sectorsubsector in the database
		List<SectorSubsector> sectorsubsectorList = sectorsubsectorRepository.findAll();
		assertThat(sectorsubsectorList).hasSize(databaseSizeBeforeCreate + 1);
		SectorSubsector testSectorsubsector = sectorsubsectorList.get(sectorsubsectorList.size() - 1);
	}

	@Test
	@Transactional
	public void createSectorsubsectorWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = sectorsubsectorRepository.findAll().size();

		// Create the Sectorsubsector with an existing ID
		sectorsubsector.setId(1L);
		SectorsubsectorDTO sectorsubsectorDTO = sectorsubsectorMapper.toDto(sectorsubsector);

		// An entity with an existing ID cannot be created, so this API call must fail
		restSectorsubsectorMockMvc
				.perform(post("/api/sectorsubsectors").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(sectorsubsectorDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Sectorsubsector in the database
		List<SectorSubsector> sectorsubsectorList = sectorsubsectorRepository.findAll();
		assertThat(sectorsubsectorList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllSectorsubsectors() throws Exception {
		// Initialize the database
		sectorsubsectorRepository.saveAndFlush(sectorsubsector);

		// Get all the sectorsubsectorList
		restSectorsubsectorMockMvc.perform(get("/api/sectorsubsectors?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(sectorsubsector.getId().intValue())));
	}

	@Test
	@Transactional
	public void getSectorsubsector() throws Exception {
		// Initialize the database
		sectorsubsectorRepository.saveAndFlush(sectorsubsector);

		// Get the sectorsubsector
		restSectorsubsectorMockMvc.perform(get("/api/sectorsubsectors/{id}", sectorsubsector.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(sectorsubsector.getId().intValue()));
	}

	@Test
	@Transactional
	public void getNonExistingSectorsubsector() throws Exception {
		// Get the sectorsubsector
		restSectorsubsectorMockMvc.perform(get("/api/sectorsubsectors/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateSectorsubsector() throws Exception {
		// Initialize the database
		sectorsubsectorRepository.saveAndFlush(sectorsubsector);

		int databaseSizeBeforeUpdate = sectorsubsectorRepository.findAll().size();

		// Update the sectorsubsector
		SectorSubsector updatedSectorsubsector = sectorsubsectorRepository.findById(sectorsubsector.getId()).get();
		// Disconnect from session so that the updates on updatedSectorsubsector are not
		// directly saved in db
		em.detach(updatedSectorsubsector);
		SectorsubsectorDTO sectorsubsectorDTO = sectorsubsectorMapper.toDto(updatedSectorsubsector);

		restSectorsubsectorMockMvc.perform(put("/api/sectorsubsectors").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(sectorsubsectorDTO))).andExpect(status().isOk());

		// Validate the Sectorsubsector in the database
		List<SectorSubsector> sectorsubsectorList = sectorsubsectorRepository.findAll();
		assertThat(sectorsubsectorList).hasSize(databaseSizeBeforeUpdate);
		SectorSubsector testSectorsubsector = sectorsubsectorList.get(sectorsubsectorList.size() - 1);
	}

	@Test
	@Transactional
	public void updateNonExistingSectorsubsector() throws Exception {
		int databaseSizeBeforeUpdate = sectorsubsectorRepository.findAll().size();

		// Create the Sectorsubsector
		SectorsubsectorDTO sectorsubsectorDTO = sectorsubsectorMapper.toDto(sectorsubsector);

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restSectorsubsectorMockMvc
				.perform(put("/api/sectorsubsectors").contentType(MediaType.APPLICATION_JSON)
						.content(TestUtil.convertObjectToJsonBytes(sectorsubsectorDTO)))
				.andExpect(status().isBadRequest());

		// Validate the Sectorsubsector in the database
		List<SectorSubsector> sectorsubsectorList = sectorsubsectorRepository.findAll();
		assertThat(sectorsubsectorList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	@Transactional
	public void deleteSectorsubsector() throws Exception {
		// Initialize the database
		sectorsubsectorRepository.saveAndFlush(sectorsubsector);

		int databaseSizeBeforeDelete = sectorsubsectorRepository.findAll().size();

		// Delete the sectorsubsector
		restSectorsubsectorMockMvc.perform(
				delete("/api/sectorsubsectors/{id}", sectorsubsector.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<SectorSubsector> sectorsubsectorList = sectorsubsectorRepository.findAll();
		assertThat(sectorsubsectorList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
