package com.emoby.mph.repository;

import com.emoby.mph.domain.CandidateLevelLanguage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CandidateLevelLanguage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateLevelLanguageRepository extends JpaRepository<CandidateLevelLanguage, Long> {
}
