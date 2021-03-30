package com.emoby.mph.repository;

import com.emoby.mph.domain.Educationlevel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Educationlevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationlevelRepository extends JpaRepository<Educationlevel, Long> {
}
