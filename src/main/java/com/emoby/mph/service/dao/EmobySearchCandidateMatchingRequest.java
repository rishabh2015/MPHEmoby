package com.emoby.mph.service.dao;

import java.util.List;

import com.emoby.mph.domain.Country;
import com.emoby.mph.domain.Experience;
import com.emoby.mph.domain.JobOpening;
import com.emoby.mph.domain.Sector;


public class EmobySearchCandidateMatchingRequest {

	private JobOpening jobOpening;

    private List<Sector> sectors;
    
    private List<Experience> experiences;

    private List<Country> nationalities;
    
    private String firstName;

    private String lastName;
    
    private String keywords;

	public JobOpening getJobOpening() {
		return jobOpening;
	}

	public void setJobOpening(JobOpening jobOpening) {
		this.jobOpening = jobOpening;
	}

	public List<Sector> getSectors() {
		return sectors;
	}

	public void setSectors(List<Sector> sectors) {
		this.sectors = sectors;
	}

	public List<Experience> getExperiences() {
		return experiences;
	}

	public void setExperiences(List<Experience> experiences) {
		this.experiences = experiences;
	}

	public List<Country> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<Country> nationalities) {
		this.nationalities = nationalities;
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
