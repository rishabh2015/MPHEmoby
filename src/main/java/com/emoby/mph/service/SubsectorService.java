package com.emoby.mph.service;

import com.emoby.mph.domain.Subsector;
import com.emoby.mph.repository.SubsectorRepository;
import com.emoby.mph.service.dto.SubsectorDTO;
import com.emoby.mph.service.mapper.SubsectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Subsector}.
 */
@Service
@Transactional
public class SubsectorService {

    private final Logger log = LoggerFactory.getLogger(SubsectorService.class);

    private final SubsectorRepository subsectorRepository;

    private final SubsectorMapper subsectorMapper;

    public SubsectorService(SubsectorRepository subsectorRepository, SubsectorMapper subsectorMapper) {
        this.subsectorRepository = subsectorRepository;
        this.subsectorMapper = subsectorMapper;
    }

    /**
     * Save a subsector.
     *
     * @param subsectorDTO the entity to save.
     * @return the persisted entity.
     */
    public SubsectorDTO save(SubsectorDTO subsectorDTO) {
        log.debug("Request to save Subsector : {}", subsectorDTO);
        Subsector subsector = subsectorMapper.toEntity(subsectorDTO);
        subsector = subsectorRepository.save(subsector);
        return subsectorMapper.toDto(subsector);
    }

    /**
     * Get all the subsectors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubsectorDTO> findAll() {
        log.debug("Request to get all Subsectors");
        return subsectorRepository.findAll().stream()
            .map(subsectorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one subsector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubsectorDTO> findOne(Long id) {
        log.debug("Request to get Subsector : {}", id);
        return subsectorRepository.findById(id)
            .map(subsectorMapper::toDto);
    }

    /**
     * Delete the subsector by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subsector : {}", id);
        subsectorRepository.deleteById(id);
    }
}
