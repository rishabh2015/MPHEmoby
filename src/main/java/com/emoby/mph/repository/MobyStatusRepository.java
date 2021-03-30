package com.emoby.mph.repository;

import com.emoby.mph.domain.MobyStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MobyStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MobyStatusRepository extends JpaRepository<MobyStatus, Long> {
}
