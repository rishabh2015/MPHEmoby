package com.emoby.mph.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.emoby.mph.domain.Jobdescription} entity.
 */
public class JobdescriptionDTO extends JobdescriptionCompactDTO implements Serializable {
    
    private Set<CountryDTO> nationalities = new HashSet<>();
    private Set<CountryDTO> locations = new HashSet<>();
    private Set<EducationlevelDTO> educationlevels = new HashSet<>();
    private Set<ProjectphaseActivityDTO> projectphaseActivities = new HashSet<>();
    private Set<TechnicalDisciplineDTO> technicalDisciplines = new HashSet<>();
    private Set<ExperienceDTO> experiences = new HashSet<>();
    private Set<LanguageDTO> languages = new HashSet<>();
    private Set<SectorDTO> sectors = new HashSet<>();

    private ContentDTO content;

    public Set<CountryDTO> getNationalities() {
        return nationalities;
    }

    public void setNationalities(Set<CountryDTO> countries) {
        this.nationalities = countries;
    }

    public Set<CountryDTO> getLocations() {
        return locations;
    }

    public void setLocations(Set<CountryDTO> countries) {
        this.locations = countries;
    }

    public Set<EducationlevelDTO> getEducationlevels() {
        return educationlevels;
    }

    public void setEducationlevels(Set<EducationlevelDTO> educationlevels) {
        this.educationlevels = educationlevels;
    }

    public Set<ProjectphaseActivityDTO> getProjectphaseActivities() {
        return projectphaseActivities;
    }

    public void setProjectphaseActivities(Set<ProjectphaseActivityDTO> projectphaseActivities) {
        this.projectphaseActivities = projectphaseActivities;
    }

    public Set<TechnicalDisciplineDTO> getTechnicalDisciplines() {
        return technicalDisciplines;
    }

    public void setTechnicalDisciplines(Set<TechnicalDisciplineDTO> technicalDisciplines) {
        this.technicalDisciplines = technicalDisciplines;
    }

    public Set<ExperienceDTO> getExperiences() {
        return experiences;
    }

    public void setExperiences(Set<ExperienceDTO> experiences) {
        this.experiences = experiences;
    }

    public Set<LanguageDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<LanguageDTO> languages) {
        this.languages = languages;
    }

    public Set<SectorDTO> getSectors() {
        return sectors;
    }

    public void setSectors(Set<SectorDTO> sectors) {
        this.sectors = sectors;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobdescriptionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creation_dt='" + getCreation_dt() + "'" +
            ", gender='" + getGender() + "'" +
            ", filename='" + getFilename() + "'" +
            ", projectId=" + getProjectId() +
            ", nationalities='" + getNationalities() + "'" +
            ", locations='" + getLocations() + "'" +
            ", educationlevels='" + getEducationlevels() + "'" +
            ", projectphaseActivities='" + getProjectphaseActivities() + "'" +
            ", technicalDisciplines='" + getTechnicalDisciplines() + "'" +
            ", experiences='" + getExperiences() + "'" +
            ", languages='" + getLanguages() + "'" +
            ", sectors='" + getSectors() + "'" +
            ", content=" + getContent() +
            "}";
    }

	public ContentDTO getContent() {
		return content;
	}

	public void setContent(ContentDTO content) {
		this.content = content;
	}
}
