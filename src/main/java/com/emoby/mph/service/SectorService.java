package com.emoby.mph.service;

import com.emoby.mph.domain.Sector;
import com.emoby.mph.repository.SectorRepository;
import com.emoby.mph.service.dto.SectorDTO;
import com.emoby.mph.service.mapper.SectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Sector}.
 */
@Service
@Transactional
public class SectorService {

    private final Logger log = LoggerFactory.getLogger(SectorService.class);

    private final SectorRepository sectorRepository;

    private final SectorMapper sectorMapper;

    public SectorService(SectorRepository sectorRepository, SectorMapper sectorMapper) {
        this.sectorRepository = sectorRepository;
        this.sectorMapper = sectorMapper;
    }

    /**
     * Save a sector.
     *
     * @param sectorDTO the entity to save.
     * @return the persisted entity.
     */
    public SectorDTO save(SectorDTO sectorDTO) {
        log.debug("Request to save Sector : {}", sectorDTO);
        Sector sector = sectorMapper.toEntity(sectorDTO);
        sector = sectorRepository.save(sector);
        return sectorMapper.toDto(sector);
    }

    /**
     * Get all the sectors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SectorDTO> findAll() {
        log.debug("Request to get all Sectors");
        return sectorRepository.findAll().stream()
            .map(sectorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one sector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SectorDTO> findOne(Long id) {
        log.debug("Request to get Sector : {}", id);
        return sectorRepository.findById(id)
            .map(sectorMapper::toDto);
    }

    /**
     * Delete the sector by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sector : {}", id);
        sectorRepository.deleteById(id);
    }
}
