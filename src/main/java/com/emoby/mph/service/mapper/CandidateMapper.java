package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.CandidateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, EducationlevelMapper.class, ExperienceMapper.class, SectorsubsectorMapper.class, ProjectphaseActivityMapper.class, TechnicalDisciplineMapper.class, JobdescriptionMapper.class, CandidateLevelLanguageMapper.class, MobyStatusMapper.class})
public interface CandidateMapper extends EntityMapper<CandidateDTO, Candidate> {

    @Mapping(source = "shortlisted.id", target = "shortlistedId")
    @Mapping(source = "placed.id", target = "placedId")
    CandidateDTO toDto(Candidate candidate);

    @Mapping(target = "removeDualNationality", ignore = true)
    @Mapping(target = "removeSectorSubsector", ignore = true)
    @Mapping(target = "removeProjectphaseActivity", ignore = true)
    @Mapping(target = "removeTechnicalDiscipline", ignore = true)
    @Mapping(target = "removeLevelLanguage", ignore = true)
    @Mapping(source = "shortlistedId", target = "shortlisted")
    @Mapping(source = "placedId", target = "placed")
    Candidate toEntity(CandidateDTO candidateDTO);

    default Candidate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Candidate candidate = new Candidate();
        candidate.setId(id);
        return candidate;
    }
}
