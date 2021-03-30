package com.emoby.mph.repository;

import com.emoby.mph.domain.LevelLanguage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LevelLanguage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LevelLanguageRepository extends JpaRepository<LevelLanguage, Long> {
}
