package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.PotentialCandidateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PotentialCandidate} and its DTO {@link PotentialCandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {JobdescriptionMapper.class, CandidateCompactMapper.class})
public interface PotentialCandidateMapper extends EntityMapper<PotentialCandidateDTO, PotentialCandidate> {

    @Mapping(source = "jobdescription.id", target = "jobdescriptionId")
    PotentialCandidateDTO toDto(PotentialCandidate potentialCandidate);

    @Mapping(source = "jobdescriptionId", target = "jobdescription")
    PotentialCandidate toEntity(PotentialCandidateDTO potentialCandidateDTO);

    default PotentialCandidate fromId(Long id) {
        if (id == null) {
            return null;
        }
        PotentialCandidate potentialCandidate = new PotentialCandidate();
        potentialCandidate.setId(id);
        return potentialCandidate;
    }
}
