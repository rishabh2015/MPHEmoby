package com.emoby.mph.service.mapper;


import com.emoby.mph.domain.*;
import com.emoby.mph.service.dao.EmobySearchCandidateMatchingRequest;
import com.emoby.mph.service.dao.EmobySearchCandidateMatchingResponse;
import com.emoby.mph.service.dto.CandidateDTO;
import com.emoby.mph.service.dto.SearchCandidatesRequestDTO;
import com.emoby.mph.service.dto.SearchCandidatesResponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Candidate} and its DTO {@link CandidateDTO}.
 */
@Mapper(componentModel = "spring", uses = {} )
public interface CandidateSearchResponseMapper extends EntityMapper<SearchCandidatesResponseDTO, EmobySearchCandidateMatchingResponse> {

    EmobySearchCandidateMatchingRequest toDto(SearchCandidatesRequestDTO candidateDTO);

}
