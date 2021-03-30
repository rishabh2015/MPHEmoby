package com.emoby.mph.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.service.dto.JobOpeningDTO;

/**
 * Spring Data  repository for the JobOpening entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {
	
    @Query("select id from JobOpening jo where jo.guid =:guid")
	Long findByUUID(@Param("guid") UUID guid);

    @Query(value = "select j from JobOpening j where delete_date is null and lower(j.title) LIKE %:title% order by lower(j.title)", countQuery = "select count(j) from JobOpening j where delete_date is null and j.title LIKE %:title%")
    Page<JobOpening> findJobOpeningByTitle(Pageable pageable, @Param("title") String title);
}
