package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.JobdescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jobdescription} and its DTO {@link JobdescriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, CountryMapper.class, EducationlevelMapper.class, ProjectphaseActivityMapper.class, TechnicalDisciplineMapper.class, ExperienceMapper.class, LanguageMapper.class, SectorMapper.class, ContentMapper.class})
public interface JobdescriptionMapper extends EntityMapper<JobdescriptionDTO, Jobdescription> {

    @Mapping(source = "project.id", target = "projectId")
    JobdescriptionDTO toDto(Jobdescription jobdescription);

    @Mapping(source = "projectId", target = "project")
    @Mapping(target = "removeNationalities", ignore = true)
    @Mapping(target = "removeLocations", ignore = true)
    @Mapping(target = "removeEducationlevel", ignore = true)
    @Mapping(target = "removeProjectphaseActivity", ignore = true)
    @Mapping(target = "removeTechnicalDiscipline", ignore = true)
    @Mapping(target = "removeExperience", ignore = true)
    @Mapping(target = "removeLanguage", ignore = true)
    @Mapping(target = "removeSector", ignore = true)
    Jobdescription toEntity(JobdescriptionDTO jobdescriptionDTO);

    default Jobdescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        Jobdescription jobdescription = new Jobdescription();
        jobdescription.setId(id);
        return jobdescription;
    }
}
