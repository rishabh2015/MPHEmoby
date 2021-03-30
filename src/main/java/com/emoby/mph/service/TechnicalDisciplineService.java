package com.emoby.mph.service;

import com.emoby.mph.domain.TechnicalDiscipline;
import com.emoby.mph.repository.TechnicalDisciplineRepository;
import com.emoby.mph.service.dto.TechnicalDisciplineDTO;
import com.emoby.mph.service.mapper.TechnicalDisciplineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TechnicalDiscipline}.
 */
@Service
@Transactional
public class TechnicalDisciplineService {

    private final Logger log = LoggerFactory.getLogger(TechnicalDisciplineService.class);

    private final TechnicalDisciplineRepository technicalDisciplineRepository;

    private final TechnicalDisciplineMapper technicalDisciplineMapper;

    public TechnicalDisciplineService(TechnicalDisciplineRepository technicalDisciplineRepository, TechnicalDisciplineMapper technicalDisciplineMapper) {
        this.technicalDisciplineRepository = technicalDisciplineRepository;
        this.technicalDisciplineMapper = technicalDisciplineMapper;
    }

    /**
     * Save a technicalDiscipline.
     *
     * @param technicalDisciplineDTO the entity to save.
     * @return the persisted entity.
     */
    public TechnicalDisciplineDTO save(TechnicalDisciplineDTO technicalDisciplineDTO) {
        log.debug("Request to save TechnicalDiscipline : {}", technicalDisciplineDTO);
        TechnicalDiscipline technicalDiscipline = technicalDisciplineMapper.toEntity(technicalDisciplineDTO);
        technicalDiscipline = technicalDisciplineRepository.save(technicalDiscipline);
        return technicalDisciplineMapper.toDto(technicalDiscipline);
    }

    /**
     * Get all the technicalDisciplines.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TechnicalDisciplineDTO> findAll() {
        log.debug("Request to get all TechnicalDisciplines");
        return technicalDisciplineRepository.findAll().stream()
            .map(technicalDisciplineMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one technicalDiscipline by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TechnicalDisciplineDTO> findOne(Long id) {
        log.debug("Request to get TechnicalDiscipline : {}", id);
        return technicalDisciplineRepository.findById(id)
            .map(technicalDisciplineMapper::toDto);
    }

    /**
     * Delete the technicalDiscipline by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TechnicalDiscipline : {}", id);
        technicalDisciplineRepository.deleteById(id);
    }
}
