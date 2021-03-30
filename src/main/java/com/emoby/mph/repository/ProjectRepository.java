package com.emoby.mph.repository;

import com.emoby.mph.domain.Project;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

import javax.validation.Valid;

/**
 * Spring Data  repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select project from Project project where project.jhi_user.id = :loginId")
    List<Project> findProjectByLoginId(@Param("loginId") Long loginId);

}
