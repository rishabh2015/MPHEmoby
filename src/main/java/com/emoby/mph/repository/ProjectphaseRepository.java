package com.emoby.mph.repository;

import com.emoby.mph.domain.Projectphase;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Projectphase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectphaseRepository extends JpaRepository<Projectphase, Long> {
}
