package com.emoby.mph.service;

import com.emoby.mph.domain.Content;
import com.emoby.mph.repository.ContentRepository;
import com.emoby.mph.service.dto.ContentDTO;
import com.emoby.mph.service.mapper.ContentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Content}.
 */
@Service
@Transactional
public class ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    public ContentService(ContentRepository contentRepository, ContentMapper contentMapper) {
        this.contentRepository = contentRepository;
        this.contentMapper = contentMapper;
    }

    /**
     * Save a content.
     *
     * @param contentDTO the entity to save.
     * @return the persisted entity.
     */
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.toEntity(contentDTO);
        content = contentRepository.save(content);
        return contentMapper.toDto(content);
    }

    /**
     * Get all the contents.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContentDTO> findAll() {
        log.debug("Request to get all Contents");
        return contentRepository.findAll().stream()
            .map(contentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one content by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContentDTO> findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        return contentRepository.findById(id)
            .map(contentMapper::toDto);
    }

    /**
     * Delete the content by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.deleteById(id);
    }
}
