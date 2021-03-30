package com.emoby.mph.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.emoby.mph.domain.enumeration.Gender;

/**
 * A DTO for the {@link com.emoby.mph.domain.Candidate} entity.
 */
public class CandidateCompactDTO implements Serializable {
    
    protected Long id;

    @NotNull
    private Gender gender;

    @NotNull
    private String last_name;

    @NotNull
    private String first_name;

    private String email;

    private String phone_number;
    
    @NotNull
    private LocalDate date_of_birth;

    @NotNull
    private UUID guid;

    private Instant creation_date;

    private Instant update_date;

    private CountryDTO nationality;

    private CountryDTO location;

    private EducationlevelDTO educationlevel;

    private ExperienceDTO experience;
    
    private MobyStatusDTO mobyStatus;

    private Long shortlistedId;

    private Long placedId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public Instant getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Instant creation_date) {
        this.creation_date = creation_date;
    }

    public Instant getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Instant update_date) {
        this.update_date = update_date;
    }

    public CountryDTO getNationality() {
        return nationality;
    }

    public void setNationality(CountryDTO country) {
        this.nationality = country;
    }

    public CountryDTO getLocation() {
        return location;
    }

    public void setLocation(CountryDTO country) {
        this.location = country;
    }

    public EducationlevelDTO getEducationlevel() {
        return educationlevel;
    }

    public void setEducationlevel(EducationlevelDTO educationlevel) {
        this.educationlevel = educationlevel;
    }

    public ExperienceDTO getExperience() {
        return experience;
    }

    public void setExperience(ExperienceDTO experience) {
        this.experience = experience;
    }

    public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public MobyStatusDTO getMobyStatus() {
		return mobyStatus;
	}

	public void setMobyStatus(MobyStatusDTO mobyStatus) {
		this.mobyStatus = mobyStatus;
	}

	public Long getShortlistedId() {
        return shortlistedId;
    }

    public void setShortlistedId(Long jobdescriptionId) {
        this.shortlistedId = jobdescriptionId;
    }

    public Long getPlacedId() {
        return placedId;
    }

    public void setPlacedId(Long jobdescriptionId) {
        this.placedId = jobdescriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateCompactDTO)) {
            return false;
        }

        return id != null && id.equals(((CandidateCompactDTO) o).id);
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
            ", phone_number='" + getPhone_number() + "'" +
            ", guid='" + getGuid() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            ", update_date='" + getUpdate_date() + "'" +
            ", nationalityId=" + getNationality() +
            ", mobyStatus=" + getMobyStatus() +
            ", locationId=" + getLocation() +
            ", educationlevelId=" + getEducationlevel() +
            ", experienceId=" + getExperience() +
            ", shortlistedId=" + getShortlistedId() +
            ", placedId=" + getPlacedId() +
            "}";
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
