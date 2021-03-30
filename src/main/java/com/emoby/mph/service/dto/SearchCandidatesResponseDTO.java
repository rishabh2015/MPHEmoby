package com.emoby.mph.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * A DTO for the {@link com.emoby.mph.domain.Candidate} entity.
 */
public class SearchCandidatesResponseDTO implements Serializable {

	private static final long serialVersionUID = 2278147353242358245L;

	private Long id;

    private UUID guid;

    private String name;

    private LocalDate date_of_birth;

    private String email;

    private String phone_number;

    private String nationality;

    private Float matching_percent;

    private String job_opening_title;

    private Long job_opening_id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Float getMatching_percent() {
        return matching_percent;
    }

	public void setMatching_percent(Float matching_percent) {
		this.matching_percent = matching_percent;
	}

	public String getJob_opening_title() {
		return job_opening_title;
	}

	public void setJob_opening_title(String job_opening_title) {
		this.job_opening_title = job_opening_title;
	}

	public Long getJob_opening_id() {
		return job_opening_id;
	}

	public void setJob_opening_id(Long job_opening_id) {
		this.job_opening_id = job_opening_id;
	}    
}
