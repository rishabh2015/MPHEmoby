package com.emoby.mph.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.emoby.mph.domain.Candidate} entity.
 */
public class SearchCandidatesRequestDTO implements Serializable {
    
	private static final long serialVersionUID = 6883998704840412881L;

	private Long jobOpeningId;

    private List<SectorDTO> sectorsId;
    
    private List<ExperienceDTO> experiencesId;

    private List<CountryDTO> nationalitiesId;
    
    private String firstName;

    private String lastName;
    
    private String keywords;

	public Long getJobOpeningId() {
		return jobOpeningId;
	}

	public void setJobOpeningId(Long jobOpeningId) {
		this.jobOpeningId = jobOpeningId;
	}

	public List<SectorDTO> getSectorsId() {
		return sectorsId;
	}

	public void setSectorsId(List<SectorDTO> sectorsId) {
		this.sectorsId = sectorsId;
	}

	public List<ExperienceDTO> getExperiencesId() {
		return experiencesId;
	}

	public void setExperiencesId(List<ExperienceDTO> experiencesId) {
		this.experiencesId = experiencesId;
	}

	public List<CountryDTO> getNationalitiesId() {
		return nationalitiesId;
	}

	public void setNationalitiesId(List<CountryDTO> nationalitiesId) {
		this.nationalitiesId = nationalitiesId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
