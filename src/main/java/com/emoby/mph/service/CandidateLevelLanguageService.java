package com.emoby.mph.service;

import com.emoby.mph.domain.CandidateLevelLanguage;
import com.emoby.mph.repository.CandidateLevelLanguageRepository;
import com.emoby.mph.service.dto.CandidateLevelLanguageDTO;
import com.emoby.mph.service.mapper.CandidateLevelLanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CandidateLevelLanguage}.
 */
@Service
@Transactional
public class CandidateLevelLanguageService {

    private final Logger log = LoggerFactory.getLogger(CandidateLevelLanguageService.class);

    private final CandidateLevelLanguageRepository candidateLevelLanguageRepository;

    private final CandidateLevelLanguageMapper candidateLevelLanguageMapper;

    public CandidateLevelLanguageService(CandidateLevelLanguageRepository candidateLevelLanguageRepository, CandidateLevelLanguageMapper candidateLevelLanguageMapper) {
        this.candidateLevelLanguageRepository = candidateLevelLanguageRepository;
        this.candidateLevelLanguageMapper = candidateLevelLanguageMapper;
    }

    /**
     * Save a candidateLevelLanguage.
     *
     * @param candidateLevelLanguageDTO the entity to save.
     * @return the persisted entity.
     */
    public CandidateLevelLanguageDTO save(CandidateLevelLanguageDTO candidateLevelLanguageDTO) {
        log.debug("Request to save CandidateLevelLanguage : {}", candidateLevelLanguageDTO);
        CandidateLevelLanguage candidateLevelLanguage = candidateLevelLanguageMapper.toEntity(candidateLevelLanguageDTO);
        candidateLevelLanguage = candidateLevelLanguageRepository.save(candidateLevelLanguage);
        return candidateLevelLanguageMapper.toDto(candidateLevelLanguage);
    }

    /**
     * Get all the candidateLevelLanguages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CandidateLevelLanguageDTO> findAll() {
        log.debug("Request to get all CandidateLevelLanguages");
        return candidateLevelLanguageRepository.findAll().stream()
            .map(candidateLevelLanguageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one candidateLevelLanguage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CandidateLevelLanguageDTO> findOne(Long id) {
        log.debug("Request to get CandidateLevelLanguage : {}", id);
        return candidateLevelLanguageRepository.findById(id)
            .map(candidateLevelLanguageMapper::toDto);
    }

    /**
     * Delete the candidateLevelLanguage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CandidateLevelLanguage : {}", id);
        candidateLevelLanguageRepository.deleteById(id);
    }
}
