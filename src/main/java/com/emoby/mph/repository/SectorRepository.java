package com.emoby.mph.repository;

import com.emoby.mph.domain.Sector;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
}
