package com.emoby.mph.service;

import com.emoby.mph.domain.Projectphase;
import com.emoby.mph.repository.ProjectphaseRepository;
import com.emoby.mph.service.dto.ProjectphaseDTO;
import com.emoby.mph.service.mapper.ProjectphaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Projectphase}.
 */
@Service
@Transactional
public class ProjectphaseService {

    private final Logger log = LoggerFactory.getLogger(ProjectphaseService.class);

    private final ProjectphaseRepository projectphaseRepository;

    private final ProjectphaseMapper projectphaseMapper;

    public ProjectphaseService(ProjectphaseRepository projectphaseRepository, ProjectphaseMapper projectphaseMapper) {
        this.projectphaseRepository = projectphaseRepository;
        this.projectphaseMapper = projectphaseMapper;
    }

    /**
     * Save a projectphase.
     *
     * @param projectphaseDTO the entity to save.
     * @return the persisted entity.
     */
    public ProjectphaseDTO save(ProjectphaseDTO projectphaseDTO) {
        log.debug("Request to save Projectphase : {}", projectphaseDTO);
        Projectphase projectphase = projectphaseMapper.toEntity(projectphaseDTO);
        projectphase = projectphaseRepository.save(projectphase);
        return projectphaseMapper.toDto(projectphase);
    }

    /**
     * Get all the projectphases.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectphaseDTO> findAll() {
        log.debug("Request to get all Projectphases");
        return projectphaseRepository.findAll().stream()
            .map(projectphaseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one projectphase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProjectphaseDTO> findOne(Long id) {
        log.debug("Request to get Projectphase : {}", id);
        return projectphaseRepository.findById(id)
            .map(projectphaseMapper::toDto);
    }

    /**
     * Delete the projectphase by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Projectphase : {}", id);
        projectphaseRepository.deleteById(id);
    }
}
