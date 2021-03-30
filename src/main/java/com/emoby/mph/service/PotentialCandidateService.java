package com.emoby.mph.service;

import com.emoby.mph.domain.PotentialCandidate;
import com.emoby.mph.repository.PotentialCandidateRepository;
import com.emoby.mph.service.dto.PotentialCandidateDTO;
import com.emoby.mph.service.mapper.PotentialCandidateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PotentialCandidate}.
 */
@Service
@Transactional
public class PotentialCandidateService {

	private final Logger log = LoggerFactory.getLogger(PotentialCandidateService.class);

	private final PotentialCandidateRepository potentialCandidateRepository;

	private final PotentialCandidateMapper potentialCandidateMapper;

	public PotentialCandidateService(PotentialCandidateRepository potentialCandidateRepository,
			PotentialCandidateMapper potentialCandidateMapper) {
		this.potentialCandidateRepository = potentialCandidateRepository;
		this.potentialCandidateMapper = potentialCandidateMapper;
	}

	/**
	 * Save a potentialCandidate.
	 *
	 * @param potentialCandidateDTO the entity to save.
	 * @return the persisted entity.
	 */
	public PotentialCandidateDTO save(PotentialCandidateDTO potentialCandidateDTO) {
		log.debug("Request to save PotentialCandidate : {}", potentialCandidateDTO);
		PotentialCandidate potentialCandidate = potentialCandidateMapper.toEntity(potentialCandidateDTO);
		potentialCandidate = potentialCandidateRepository.save(potentialCandidate);
		return potentialCandidateMapper.toDto(potentialCandidate);
	}

	/**
	 * Get all the potentialCandidates.
	 *
	 * @return the list of entities.
	 */
	@Transactional(readOnly = true)
	public List<PotentialCandidateDTO> findAll() {
		log.debug("Request to get all PotentialCandidates");
		return potentialCandidateRepository.findAll().stream().map(potentialCandidateMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one potentialCandidate by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Transactional(readOnly = true)
	public Optional<PotentialCandidateDTO> findOne(Long id) {
		log.debug("Request to get PotentialCandidate : {}", id);
		return potentialCandidateRepository.findById(id).map(potentialCandidateMapper::toDto);
	}

	/**
	 * Delete the potentialCandidate by id.
	 *
	 * @param id the id of the entity.
	 */
	public void delete(Long id) {
		log.debug("Request to delete PotentialCandidate : {}", id);
		potentialCandidateRepository.deleteById(id);
	}

	public Page<PotentialCandidateDTO> findAllPotentialCandidateByJobdescriptionId(Pageable pageable,
			Long jobdescriptionId) {
		log.debug("Request to get all findAllPotentialCandidateByJobdescriptionId");

		return potentialCandidateRepository.findAllPotentialCandidateByJobdescriptionId(pageable, jobdescriptionId)
				.map(potentialCandidateMapper::toDto);
	}

	public Page<PotentialCandidateDTO> findAllPotentialCandidateShortlistedByJobdescriptionId(Pageable pageable,
			Long jobdescriptionId) {
		log.debug("Request to get all findAllPotentialCandidateShortlistedByJobdescriptionId");

		return potentialCandidateRepository.findAllPotentialCandidateShortlistedByJobdescriptionId(pageable, jobdescriptionId)
				.map(potentialCandidateMapper::toDto);
	}

	public Page<PotentialCandidateDTO> findAllPotentialCandidatePlacedByJobdescriptionId(Pageable pageable,
			Long jobdescriptionId) {
		log.debug("Request to get all findAllPotentialCandidatePlacedByJobdescriptionId");

		return potentialCandidateRepository.findAllPotentialCandidatePlacedByJobdescriptionId(pageable, jobdescriptionId)
				.map(potentialCandidateMapper::toDto);
	}

}
