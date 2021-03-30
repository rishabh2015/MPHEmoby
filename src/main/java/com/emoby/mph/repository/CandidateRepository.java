package com.emoby.mph.repository;

import com.emoby.mph.domain.Candidate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Candidate entity.
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    @Query(value = "select distinct candidate from Candidate candidate left join fetch candidate.dualNationalities left join fetch candidate.sectorSubsectors left join fetch candidate.projectphaseActivities left join fetch candidate.technicalDisciplines",
        countQuery = "select count(distinct candidate) from Candidate candidate")
    Page<Candidate> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct candidate from Candidate candidate left join fetch candidate.dualNationalities left join fetch candidate.sectorSubsectors left join fetch candidate.projectphaseActivities left join fetch candidate.technicalDisciplines")
    List<Candidate> findAllWithEagerRelationships();

    @Query("select candidate from Candidate candidate left join fetch candidate.dualNationalities left join fetch candidate.sectorSubsectors left join fetch candidate.projectphaseActivities left join fetch candidate.technicalDisciplines where candidate.id =:id")
    Optional<Candidate> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select count(candidate) from Candidate candidate where candidate.shortlisted.id =:jobdescriptionId")
	int countShortlistedByJobdescription(@Param("jobdescriptionId") Long jobdescriptionId);
    
    @Query("select count(candidate) from Candidate candidate where candidate.placed.id =:jobdescriptionId")
    int countPlacedByJobdescription(@Param("jobdescriptionId") Long jobdescriptionId);

    @Modifying
    @Query("UPDATE Candidate candidate SET shortlisted.id = :jobdescriptionId WHERE candidate.id = :candidateId and (candidate.shortlisted = null or (:jobdescriptionId = null and candidate.placed = null))")
    @Transactional
	int setShortlisted(@Param("candidateId") Long candidateId, @Param("jobdescriptionId") Long jobdescriptionId);

    @Modifying
    @Query("UPDATE Candidate candidate SET placed.id = :jobdescriptionId WHERE candidate.id = :candidateId and ((candidate.placed = null and candidate.shortlisted = :jobdescriptionId) or :jobdescriptionId = null)")
    @Transactional
	int setPlaced(@Param("candidateId") Long candidateId, @Param("jobdescriptionId") Long jobdescriptionId);
}
