package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.JobdescriptionCompactDTO;
import com.emoby.mph.service.dto.JobdescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jobdescription} and its DTO {@link JobdescriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProjectMapper.class, ContentMapper.class})
public interface JobdescriptionCompactMapper extends EntityMapper<JobdescriptionCompactDTO, Jobdescription> {

    @Mapping(source = "project.id", target = "projectId")
    //@Mapping(source = "content.id", target = "contentId")
    JobdescriptionCompactDTO toDto(Jobdescription jobdescription);

    @Mapping(source = "projectId", target = "project")
    //@Mapping(source = "contentId", target = "content")
    Jobdescription toEntity(JobdescriptionCompactDTO jobdescriptionDTO);

    default Jobdescription fromId(Long id) {
        if (id == null) {
            return null;
        }
        Jobdescription jobdescription = new Jobdescription();
        jobdescription.setId(id);
        return jobdescription;
    }
}
