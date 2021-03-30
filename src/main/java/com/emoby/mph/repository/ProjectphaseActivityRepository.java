package com.emoby.mph.repository;

import com.emoby.mph.domain.ProjectphaseActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProjectphaseActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectphaseActivityRepository extends JpaRepository<ProjectphaseActivity, Long> {
}
