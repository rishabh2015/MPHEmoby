package com.emoby.mph.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.emoby.mph.domain.Candidate;
import com.emoby.mph.service.dto.CandidateCompactDTO;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateCompactDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, EducationlevelMapper.class, ExperienceMapper.class, JobdescriptionMapper.class, MobyStatusMapper.class})
public interface CandidateCompactMapper extends EntityMapper<CandidateCompactDTO, Candidate> {

    @Mapping(source = "shortlisted.id", target = "shortlistedId")
    @Mapping(source = "placed.id", target = "placedId")
    CandidateCompactDTO toDto(Candidate candidate);

    @Mapping(source = "shortlistedId", target = "shortlisted")
    @Mapping(source = "placedId", target = "placed")
    Candidate toEntity(CandidateCompactDTO candidateDTO);

    default Candidate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidate candidate = new Candidate();
        candidate.setId(id);
        return candidate;
    }
}
