package com.emoby.mph.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.emoby.mph.service.dto.JobOpeningCompactDTO;
import com.emoby.mph.service.dto.JobOpeningDTO;

/**
 * Service Interface for managing {@link com.emoby.mph.domain.JobOpening}.
 */
public interface JobOpeningService {

    /**
     * Save a jobOpening.
     *
     * @param jobOpeningDTO the entity to save.
     * @return the persisted entity.
     */
    JobOpeningDTO save(JobOpeningDTO jobOpeningDTO);

    /**
     * Get all the jobOpenings.
     *
     * @return the list of entities.
     */
    List<JobOpeningDTO> findAll();

    /**
     * Get all the jobOpenings.
     *
     * @return the list of entities.
     */
    List<JobOpeningCompactDTO> getJobOpeningByTitle(String title);


    /**
     * Get the "id" jobOpening.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobOpeningDTO> findOne(Long id);

    /**
     * Delete the "id" jobOpening.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * find the "uuid" jobOpening.
     *
     * @param UUID the UUID of the entity.
     */
    Long findIdJobOpeningByUUID(UUID uuid);
    
    /**
     * createOrUpdateJobOpening.
     *
     * @param UUID the UUID of the entity.
     * @throws IOException 
     */
    JobOpeningCompactDTO createOrUpdateJobOpening(JobOpeningCompactDTO jobOpeningCompactDTO) throws IOException;
}
