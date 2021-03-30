package com.emoby.mph.service.mapper;


import org.mapstruct.Mapper;

import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.service.dto.JobOpeningDTO;

/**
 * Mapper for the entity {@link JobOpening} and its DTO {@link JobOpeningDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobOpeningMapper extends EntityMapper<JobOpeningDTO, JobOpening> {



    default JobOpening fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobOpening jobOpening = new JobOpening();
        jobOpening.setId(id);
        return jobOpening;
    }
}
