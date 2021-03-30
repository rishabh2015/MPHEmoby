package com.emoby.mph.repository;

import com.emoby.mph.domain.Jobdescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Jobdescription entity.
 */
@Repository
public interface JobdescriptionRepository extends JpaRepository<Jobdescription, Long> {

    @Query(value = "select distinct jobdescription from Jobdescription jobdescription left join fetch jobdescription.nationalities left join fetch jobdescription.locations left join fetch jobdescription.educationlevels left join fetch jobdescription.projectphaseActivities left join fetch jobdescription.technicalDisciplines left join fetch jobdescription.experiences left join fetch jobdescription.languages left join fetch jobdescription.sectors",
        countQuery = "select count(distinct jobdescription) from Jobdescription jobdescription")
    Page<Jobdescription> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct jobdescription from Jobdescription jobdescription left join fetch jobdescription.nationalities left join fetch jobdescription.locations left join fetch jobdescription.educationlevels left join fetch jobdescription.projectphaseActivities left join fetch jobdescription.technicalDisciplines left join fetch jobdescription.experiences left join fetch jobdescription.languages left join fetch jobdescription.sectors")
    List<Jobdescription> findAllWithEagerRelationships();

    @Query("select jobdescription from Jobdescription jobdescription left join fetch jobdescription.nationalities left join fetch jobdescription.locations left join fetch jobdescription.educationlevels left join fetch jobdescription.projectphaseActivities left join fetch jobdescription.technicalDisciplines left join fetch jobdescription.experiences left join fetch jobdescription.languages left join fetch jobdescription.sectors where jobdescription.id =:id")
    Optional<Jobdescription> findOneWithEagerRelationships(@Param("id") Long id);
	
    @Query(value = "select distinct j from Jobdescription j left join j.project p left join p.jhi_user u where u.id =:id", 
    	countQuery = "select count(distinct j) from Jobdescription j left join j.project p left join p.jhi_user u where u.id =:id")
    Page<Jobdescription> findAllJobDescriptionByUsername(Pageable pageable, @Param("id") Long id);

    @Query(value = "select count(distinct j) > 0 from Jobdescription j left join j.project p left join p.jhi_user u where u.id =:userId and j.id =:jobdescriptionId")
	boolean checkJobdescriptionAutorization(@Param("jobdescriptionId") Long jobdescriptionId, @Param("userId") Long userId);
}
