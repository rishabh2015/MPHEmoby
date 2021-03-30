package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.ProjectphaseActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectphaseActivity} and its DTO {@link ProjectphaseActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectphaseMapper.class, ActivityMapper.class})
public interface ProjectphaseActivityMapper extends EntityMapper<ProjectphaseActivityDTO, ProjectphaseActivity> {

    @Mapping(source = "projectphase.id", target = "projectphaseId")
    @Mapping(source = "projectphase.libelle", target = "projectphaseLibelle")
    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "activity.libelle", target = "activityLibelle")
    ProjectphaseActivityDTO toDto(ProjectphaseActivity projectphaseActivity);

    @Mapping(source = "projectphaseId", target = "projectphase")
    @Mapping(source = "activityId", target = "activity")
    ProjectphaseActivity toEntity(ProjectphaseActivityDTO projectphaseActivityDTO);

    default ProjectphaseActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectphaseActivity projectphaseActivity = new ProjectphaseActivity();
        projectphaseActivity.setId(id);
        return projectphaseActivity;
    }
}
