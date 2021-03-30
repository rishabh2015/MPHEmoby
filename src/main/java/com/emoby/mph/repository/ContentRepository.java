package com.emoby.mph.repository;

import com.emoby.mph.domain.Content;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Content entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
