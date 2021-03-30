package com.emoby.mph.repository;

import com.emoby.mph.domain.SectorSubsector;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sectorsubsector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectorsubsectorRepository extends JpaRepository<SectorSubsector, Long> {
}
