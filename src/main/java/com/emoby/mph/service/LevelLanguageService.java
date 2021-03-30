package com.emoby.mph.service;

import com.emoby.mph.domain.LevelLanguage;
import com.emoby.mph.repository.LevelLanguageRepository;
import com.emoby.mph.service.dto.LevelLanguageDTO;
import com.emoby.mph.service.mapper.LevelLanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LevelLanguage}.
 */
@Service
@Transactional
public class LevelLanguageService {

    private final Logger log = LoggerFactory.getLogger(LevelLanguageService.class);

    private final LevelLanguageRepository levelLanguageRepository;

    private final LevelLanguageMapper levelLanguageMapper;

    public LevelLanguageService(LevelLanguageRepository levelLanguageRepository, LevelLanguageMapper levelLanguageMapper) {
        this.levelLanguageRepository = levelLanguageRepository;
        this.levelLanguageMapper = levelLanguageMapper;
    }

    /**
     * Save a levelLanguage.
     *
     * @param levelLanguageDTO the entity to save.
     * @return the persisted entity.
     */
    public LevelLanguageDTO save(LevelLanguageDTO levelLanguageDTO) {
        log.debug("Request to save LevelLanguage : {}", levelLanguageDTO);
        LevelLanguage levelLanguage = levelLanguageMapper.toEntity(levelLanguageDTO);
        levelLanguage = levelLanguageRepository.save(levelLanguage);
        return levelLanguageMapper.toDto(levelLanguage);
    }

    /**
     * Get all the levelLanguages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LevelLanguageDTO> findAll() {
        log.debug("Request to get all LevelLanguages");
        return levelLanguageRepository.findAll().stream()
            .map(levelLanguageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one levelLanguage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LevelLanguageDTO> findOne(Long id) {
        log.debug("Request to get LevelLanguage : {}", id);
        return levelLanguageRepository.findById(id)
            .map(levelLanguageMapper::toDto);
    }

    /**
     * Delete the levelLanguage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LevelLanguage : {}", id);
        levelLanguageRepository.deleteById(id);
    }
}
