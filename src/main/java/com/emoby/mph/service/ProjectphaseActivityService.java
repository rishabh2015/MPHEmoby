package com.emoby.mph.service;

import com.emoby.mph.domain.ProjectphaseActivity;
import com.emoby.mph.repository.ProjectphaseActivityRepository;
import com.emoby.mph.service.dto.ProjectphaseActivityDTO;
import com.emoby.mph.service.mapper.ProjectphaseActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProjectphaseActivity}.
 */
@Service
@Transactional
public class ProjectphaseActivityService {

    private final Logger log = LoggerFactory.getLogger(ProjectphaseActivityService.class);

    private final ProjectphaseActivityRepository projectphaseActivityRepository;

    private final ProjectphaseActivityMapper projectphaseActivityMapper;

    public ProjectphaseActivityService(ProjectphaseActivityRepository projectphaseActivityRepository, ProjectphaseActivityMapper projectphaseActivityMapper) {
        this.projectphaseActivityRepository = projectphaseActivityRepository;
        this.projectphaseActivityMapper = projectphaseActivityMapper;
    }

    /**
     * Save a projectphaseActivity.
     *
     * @param projectphaseActivityDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectphaseActivityDTO save(ProjectphaseActivityDTO projectphaseActivityDTO) {
        log.debug("Request to save ProjectphaseActivity : {}", projectphaseActivityDTO);
        ProjectphaseActivity projectphaseActivity = projectphaseActivityMapper.toEntity(projectphaseActivityDTO);
        projectphaseActivity = projectphaseActivityRepository.save(projectphaseActivity);
        return projectphaseActivityMapper.toDto(projectphaseActivity);
    }

    /**
     * Get all the projectphaseActivities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectphaseActivityDTO> findAll() {
        log.debug("Request to get all ProjectphaseActivities");
        return projectphaseActivityRepository.findAll().stream()
            .map(projectphaseActivityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one projectphaseActivity by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectphaseActivityDTO> findOne(Long id) {
        log.debug("Request to get ProjectphaseActivity : {}", id);
        return projectphaseActivityRepository.findById(id)
            .map(projectphaseActivityMapper::toDto);
    }

    /**
     * Delete the projectphaseActivity by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProjectphaseActivity : {}", id);
        projectphaseActivityRepository.deleteById(id);
    }
}
