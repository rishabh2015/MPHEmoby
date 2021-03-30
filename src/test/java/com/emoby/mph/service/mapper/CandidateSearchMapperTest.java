package com.emoby.mph.service.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.emoby.mph.EmobyMphApp;
import com.emoby.mph.service.dao.EmobySearchCandidateMatchingRequest;
import com.emoby.mph.service.dto.CountryDTO;
import com.emoby.mph.service.dto.ExperienceDTO;
import com.emoby.mph.service.dto.SearchCandidatesRequestDTO;
import com.emoby.mph.service.dto.SectorDTO;

@SpringBootTest(classes = EmobyMphApp.class)
public class CandidateSearchMapperTest {

    private CandidateSearchRequestMapper candidateSearchMapper;

    @BeforeEach
    public void setUp() {
    	candidateSearchMapper = new CandidateSearchRequestMapperImpl();
    }

    @Test
    public void testMapper() {
    	SearchCandidatesRequestDTO candidateDTO = new SearchCandidatesRequestDTO();
    	List<ExperienceDTO> experiencesId = new ArrayList<ExperienceDTO>();
    	
    	ExperienceDTO experienceDTO = new ExperienceDTO();
    	experienceDTO.setCode("EXP");
    	experienceDTO.setLibelle("Libelle");
    	experiencesId.add(experienceDTO);
    	
    	List<SectorDTO> sectorsId = new ArrayList<SectorDTO>();
    	SectorDTO sectorDTO = new SectorDTO();
    	sectorDTO.setCode("SEC");
    	sectorDTO.setLibelle("Libelle");
    	sectorsId.add(sectorDTO);
    	
    	List<CountryDTO> countryId = new ArrayList<CountryDTO>();
    	CountryDTO countryDTO = new CountryDTO();
    	countryDTO.setCode("COU");
    	countryDTO.setLibelle("Libelle");
    	countryId.add(countryDTO);
    	
    	
    	candidateDTO.setFirstName("Firstname");
    	candidateDTO.setLastName("Lastname");
		candidateDTO.setExperiencesId(experiencesId);
		candidateDTO.setNationalitiesId(countryId);
		candidateDTO.setSectorsId(sectorsId);
		candidateDTO.setKeywords("Bla bala bla keyword");
    	
//		EmobySearchCandidateMatchingRequest request = candidateSearchMapper.toEntity(candidateDTO);
//		
//		assertEquals(request.getKeywords(), candidateDTO.getKeywords());
//		assertEquals(request.getFirstName(), candidateDTO.getFirstName());
//		assertEquals(request.getLastName(), candidateDTO.getLastName());
//		assertEquals(request.getExperiences().get(0).getCode(), "EXP");
//		assertEquals(request.getSectors().get(0).getCode(), "SEC");
//		assertEquals(request.getNationalities().get(0).getCode(), "COU");
		
    }
}
