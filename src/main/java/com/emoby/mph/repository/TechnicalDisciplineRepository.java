package com.emoby.mph.repository;

import com.emoby.mph.domain.TechnicalDiscipline;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TechnicalDiscipline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TechnicalDisciplineRepository extends JpaRepository<TechnicalDiscipline, Long> {
}
