package com.emoby.mph.service.mapper;


import org.mapstruct.Mapper;

import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.service.dto.JobOpeningCompactDTO;

/**
 * Mapper for the entity {@link JobOpening} and its DTO {@link JobOpeningCompactDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobOpeningCompactMapper extends EntityMapper<JobOpeningCompactDTO, JobOpening> {



    default JobOpening fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobOpening jobOpening = new JobOpening();
        jobOpening.setId(id);
        return jobOpening;
    }
}
