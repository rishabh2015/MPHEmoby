package com.emoby.mph.service;

import com.emoby.mph.domain.Educationlevel;
import com.emoby.mph.repository.EducationlevelRepository;
import com.emoby.mph.service.dto.EducationlevelDTO;
import com.emoby.mph.service.mapper.EducationlevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Educationlevel}.
 */
@Service
@Transactional
public class EducationlevelService {

    private final Logger log = LoggerFactory.getLogger(EducationlevelService.class);

    private final EducationlevelRepository educationlevelRepository;

    private final EducationlevelMapper educationlevelMapper;

    public EducationlevelService(EducationlevelRepository educationlevelRepository, EducationlevelMapper educationlevelMapper) {
        this.educationlevelRepository = educationlevelRepository;
        this.educationlevelMapper = educationlevelMapper;
    }

    /**
     * Save a educationlevel.
     *
     * @param educationlevelDTO the entity to save.
     * @return the persisted entity.
     */
    public EducationlevelDTO save(EducationlevelDTO educationlevelDTO) {
        log.debug("Request to save Educationlevel : {}", educationlevelDTO);
        Educationlevel educationlevel = educationlevelMapper.toEntity(educationlevelDTO);
        educationlevel = educationlevelRepository.save(educationlevel);
        return educationlevelMapper.toDto(educationlevel);
    }

    /**
     * Get all the educationlevels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EducationlevelDTO> findAll() {
        log.debug("Request to get all Educationlevels");
        return educationlevelRepository.findAll().stream()
            .map(educationlevelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one educationlevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EducationlevelDTO> findOne(Long id) {
        log.debug("Request to get Educationlevel : {}", id);
        return educationlevelRepository.findById(id)
            .map(educationlevelMapper::toDto);
    }

    /**
     * Delete the educationlevel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Educationlevel : {}", id);
        educationlevelRepository.deleteById(id);
    }
}
