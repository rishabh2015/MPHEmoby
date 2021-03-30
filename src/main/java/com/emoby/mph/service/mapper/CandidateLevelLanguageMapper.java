package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dto.CandidateLevelLanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CandidateLevelLanguage} and its DTO {@link CandidateLevelLanguageDTO}.
 */
@Mapper(componentModel = "spring", uses = {LevelLanguageMapper.class, LanguageMapper.class, CandidateMapper.class})
public interface CandidateLevelLanguageMapper extends EntityMapper<CandidateLevelLanguageDTO, CandidateLevelLanguage> {

    @Mapping(source = "candidate.id", target = "candidateId")
    CandidateLevelLanguageDTO toDto(CandidateLevelLanguage candidateLevelLanguage);

    @Mapping(source = "candidateId", target = "candidate")
    CandidateLevelLanguage toEntity(CandidateLevelLanguageDTO candidateLevelLanguageDTO);

    default CandidateLevelLanguage fromId(Long id) {
        if (id == null) {
            return null;
        }
        CandidateLevelLanguage candidateLevelLanguage = new CandidateLevelLanguage();
        candidateLevelLanguage.setId(id);
        return candidateLevelLanguage;
    }
}
