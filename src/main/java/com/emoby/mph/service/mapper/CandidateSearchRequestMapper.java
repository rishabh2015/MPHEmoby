package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dao.EmobySearchCandidateMatchingRequest;
import com.emoby.mph.service.dto.CandidateDTO;
import com.emoby.mph.service.dto.SearchCandidatesRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {CountryMapper.class, ExperienceMapper.class, SectorMapper.class} )
public interface CandidateSearchRequestMapper extends EntityMapper<SearchCandidatesRequestDTO, EmobySearchCandidateMatchingRequest> {

	@Mapping(source = "sectorsId", target = "sectors")
	@Mapping(source = "nationalitiesId", target = "nationalities")
	@Mapping(source = "experiencesId", target = "experiences")
    EmobySearchCandidateMatchingRequest toEntity(SearchCandidatesRequestDTO candidateDTO);

}
