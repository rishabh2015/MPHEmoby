package com.emoby.mph.service;

import com.emoby.mph.domain.SectorSubsector;
import com.emoby.mph.repository.SectorsubsectorRepository;
import com.emoby.mph.service.dto.SectorsubsectorDTO;
import com.emoby.mph.service.mapper.SectorsubsectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SectorSubsector}.
 */
@Service
@Transactional
public class SectorsubsectorService {

    private final Logger log = LoggerFactory.getLogger(SectorsubsectorService.class);

    private final SectorsubsectorRepository sectorsubsectorRepository;

    private final SectorsubsectorMapper sectorsubsectorMapper;

    public SectorsubsectorService(SectorsubsectorRepository sectorsubsectorRepository, SectorsubsectorMapper sectorsubsectorMapper) {
        this.sectorsubsectorRepository = sectorsubsectorRepository;
        this.sectorsubsectorMapper = sectorsubsectorMapper;
    }

    /**
     * Save a sectorsubsector.
     *
     * @param sectorsubsectorDTO the entity to save.
     * @return the persisted entity.
     */
    public SectorsubsectorDTO save(SectorsubsectorDTO sectorsubsectorDTO) {
        log.debug("Request to save Sectorsubsector : {}", sectorsubsectorDTO);
        SectorSubsector sectorsubsector = sectorsubsectorMapper.toEntity(sectorsubsectorDTO);
        sectorsubsector = sectorsubsectorRepository.save(sectorsubsector);
        return sectorsubsectorMapper.toDto(sectorsubsector);
    }

    /**
     * Get all the sectorsubsectors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SectorsubsectorDTO> findAll() {
        log.debug("Request to get all Sectorsubsectors");
        return sectorsubsectorRepository.findAll().stream()
            .map(sectorsubsectorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sectorsubsector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SectorsubsectorDTO> findOne(Long id) {
        log.debug("Request to get Sectorsubsector : {}", id);
        return sectorsubsectorRepository.findById(id)
            .map(sectorsubsectorMapper::toDto);
    }

    /**
     * Delete the sectorsubsector by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sectorsubsector : {}", id);
        sectorsubsectorRepository.deleteById(id);
    }
}
