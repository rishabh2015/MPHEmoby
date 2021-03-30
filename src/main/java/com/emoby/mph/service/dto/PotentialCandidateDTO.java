package com.emoby.mph.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.emoby.mph.domain.PotentialCandidate} entity.
 */
public class PotentialCandidateDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Float matching_percent;

    @NotNull
    private Instant creation_dt;

    private Long jobdescriptionId;

    private CandidateCompactDTO candidate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMatching_percent() {
        return matching_percent;
    }

    public void setMatching_percent(Float matching_percent) {
        this.matching_percent = matching_percent;
    }

    public Instant getCreation_dt() {
        return creation_dt;
    }

    public void setCreation_dt(Instant creation_dt) {
        this.creation_dt = creation_dt;
    }

    public Long getJobdescriptionId() {
        return jobdescriptionId;
    }

    public void setJobdescriptionId(Long jobdescriptionId) {
        this.jobdescriptionId = jobdescriptionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PotentialCandidateDTO)) {
            return false;
        }

        return id != null && id.equals(((PotentialCandidateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PotentialCandidateDTO{" +
            "id=" + getId() +
            ", matching_percent=" + getMatching_percent() +
            ", creation_dt='" + getCreation_dt() + "'" +
            ", jobdescriptionId=" + getJobdescriptionId() +
            ", candidateId=" + getCandidate() +
            "}";
    }

	public CandidateCompactDTO getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateCompactDTO candidate) {
		this.candidate = candidate;
	}

}
