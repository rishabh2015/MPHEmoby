package com.emoby.mph.service;

import com.emoby.mph.domain.MobyStatus;
import com.emoby.mph.repository.MobyStatusRepository;
import com.emoby.mph.service.dto.MobyStatusDTO;
import com.emoby.mph.service.mapper.MobyStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MobyStatus}.
 */
@Service
@Transactional
public class MobyStatusService {

    private final Logger log = LoggerFactory.getLogger(MobyStatusService.class);

    private final MobyStatusRepository mobyStatusRepository;

    private final MobyStatusMapper mobyStatusMapper;

    public MobyStatusService(MobyStatusRepository mobyStatusRepository, MobyStatusMapper mobyStatusMapper) {
        this.mobyStatusRepository = mobyStatusRepository;
        this.mobyStatusMapper = mobyStatusMapper;
    }

    /**
     * Save a mobyStatus.
     *
     * @param mobyStatusDTO the entity to save.
     * @return the persisted entity.
     */
    public MobyStatusDTO save(MobyStatusDTO mobyStatusDTO) {
        log.debug("Request to save MobyStatus : {}", mobyStatusDTO);
        MobyStatus mobyStatus = mobyStatusMapper.toEntity(mobyStatusDTO);
        mobyStatus = mobyStatusRepository.save(mobyStatus);
        return mobyStatusMapper.toDto(mobyStatus);
    }

    /**
     * Get all the mobyStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MobyStatusDTO> findAll() {
        log.debug("Request to get all MobyStatuses");
        return mobyStatusRepository.findAll().stream()
            .map(mobyStatusMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one mobyStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MobyStatusDTO> findOne(Long id) {
        log.debug("Request to get MobyStatus : {}", id);
        return mobyStatusRepository.findById(id)
            .map(mobyStatusMapper::toDto);
    }

    /**
     * Delete the mobyStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MobyStatus : {}", id);
        mobyStatusRepository.deleteById(id);
    }
}
