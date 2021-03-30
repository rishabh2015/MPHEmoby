package com.emoby.mph.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emoby.mph.domain.Candidate} entity.
 */
public class CandidateDTO extends CandidateCompactDTO implements Serializable {
    
    @Lob
    private String text_clean;

    @Lob
    private String comment;

    private Set<CountryDTO> dualNationalities = new HashSet<>();

    private CountryDTO location;

    private Set<SectorsubsectorDTO> sectorSubsectors = new HashSet<>();
    private Set<ProjectphaseActivityDTO> projectphaseActivities = new HashSet<>();
    private Set<TechnicalDisciplineDTO> technicalDisciplines = new HashSet<>();
    private Set<CandidateLevelLanguageDTO> levelLanguages = new HashSet<>();
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateDTO)) {
            return false;
        }

        return id != null && id.equals(((CandidateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateDTO{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", date_of_birth='" + getDate_of_birth() + "'" +
            ", text_clean='" + getText_clean() + "'" +
            ", guid='" + getGuid() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            ", update_date='" + getUpdate_date() + "'" +
            ", comment='" + getComment() + "'" +
            ", nationalityId=" + getNationality() +
            ", dualNationalities='" + getDualNationalities() + "'" +
            ", locationId=" + getLocation() +
            ", educationlevelId=" + getEducationlevel() +
            ", experienceId=" + getExperience() +
            ", sectorSubsectors='" + getSectorSubsectors() + "'" +
            ", projectphaseActivities='" + getProjectphaseActivities() + "'" +
            ", technicalDisciplines='" + getTechnicalDisciplines() + "'" +
            ", shortlistedId=" + getShortlistedId() +
            ", placedId=" + getPlacedId() +
            ", levelLanguages=" + getLevelLanguages() +
            "}";
    }

	public String getText_clean() {
		return text_clean;
	}

	public void setText_clean(String text_clean) {
		this.text_clean = text_clean;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<CountryDTO> getDualNationalities() {
		return dualNationalities;
	}

	public void setDualNationalities(Set<CountryDTO> dualNationalities) {
		this.dualNationalities = dualNationalities;
	}

	public CountryDTO getLocation() {
		return location;
	}

	public void setLocation(CountryDTO location) {
		this.location = location;
	}

	public Set<SectorsubsectorDTO> getSectorSubsectors() {
		return sectorSubsectors;
	}

	public void setSectorSubsectors(Set<SectorsubsectorDTO> sectorSubsectors) {
		this.sectorSubsectors = sectorSubsectors;
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

	public Set<CandidateLevelLanguageDTO> getLevelLanguages() {
		return levelLanguages;
	}

	public void setLevelLanguages(Set<CandidateLevelLanguageDTO> levelLanguages) {
		this.levelLanguages = levelLanguages;
	}
}
