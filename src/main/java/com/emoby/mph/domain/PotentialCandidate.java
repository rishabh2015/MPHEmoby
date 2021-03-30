package com.emoby.mph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PotentialCandidate.
 */
@Entity
@Table(name = "potential_candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PotentialCandidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matching_percent", nullable = false)
    private Float matching_percent;

    @NotNull
    @Column(name = "creation_dt", nullable = false)
    private Instant creation_dt;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties(value = "potentialCandidates", allowSetters = true)
    private Jobdescription jobdescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties(value = "potentialCandidates", allowSetters = true)
    private Candidate candidate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMatching_percent() {
        return matching_percent;
    }

    public PotentialCandidate matching_percent(Float matching_percent) {
        this.matching_percent = matching_percent;
        return this;
    }

    public void setMatching_percent(Float matching_percent) {
        this.matching_percent = matching_percent;
    }

    public Instant getCreation_dt() {
        return creation_dt;
    }

    public PotentialCandidate creation_dt(Instant creation_dt) {
        this.creation_dt = creation_dt;
        return this;
    }

    public void setCreation_dt(Instant creation_dt) {
        this.creation_dt = creation_dt;
    }

    public Jobdescription getJobdescription() {
        return jobdescription;
    }

    public PotentialCandidate jobdescription(Jobdescription jobdescription) {
        this.jobdescription = jobdescription;
        return this;
    }

    public void setJobdescription(Jobdescription jobdescription) {
        this.jobdescription = jobdescription;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public PotentialCandidate candidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PotentialCandidate)) {
            return false;
        }
        return id != null && id.equals(((PotentialCandidate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PotentialCandidate{" +
            "id=" + getId() +
            ", matching_percent=" + getMatching_percent() +
            ", creation_dt='" + getCreation_dt() + "'" +
            "}";
    }
}
