package com.emoby.mph.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emoby.mph.domain.PotentialCandidate;

/**
 * Spring Data  repository for the PotentialCandidate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PotentialCandidateRepository extends JpaRepository<PotentialCandidate, Long> {
	
	@Query(value = "select * from potential_candidate p, candidate c where p.candidate_id = c.id and (c.shortlisted_id = :jobdescriptionId or c.shortlisted_id is null) and p.jobdescription_id = :jobdescriptionId order by matching_percent desc", nativeQuery = true,
            countQuery = "select count(p.id) from potential_candidate p, candidate c where p.candidate_id = c.id and (c.shortlisted_id = :jobdescriptionId or c.shortlisted_id is null)  and p.jobdescription_id = :jobdescriptionId")
	Page<PotentialCandidate> findAllPotentialCandidateByJobdescriptionId(Pageable pageable, @Param("jobdescriptionId") Long jobdescriptionId);

	@Query(value = "select * from potential_candidate p, candidate c where p.candidate_id = c.id and c.shortlisted_id = :jobdescriptionId and p.jobdescription_id = :jobdescriptionId order by matching_percent desc", nativeQuery = true,
            countQuery = "select count(p.id) from potential_candidate p, candidate c where p.candidate_id = c.id and c.shortlisted_id = :jobdescriptionId and p.jobdescription_id = :jobdescriptionId")
	Page<PotentialCandidate> findAllPotentialCandidateShortlistedByJobdescriptionId(Pageable pageable, @Param("jobdescriptionId") Long jobdescriptionId);

	@Query(value = "select * from potential_candidate p, candidate c where p.candidate_id = c.id and c.placed_id = :jobdescriptionId and p.jobdescription_id = :jobdescriptionId order by matching_percent desc", nativeQuery = true,
            countQuery = "select count(p.id) from potential_candidate p, candidate c where p.candidate_id = c.id and c.placed_id = :jobdescriptionId and p.jobdescription_id = :jobdescriptionId")
	Page<PotentialCandidate> findAllPotentialCandidatePlacedByJobdescriptionId(Pageable pageable, @Param("jobdescriptionId")  Long jobdescriptionId);
	
}
