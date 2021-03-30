package com.emoby.mph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CandidateLevelLanguage.
 */
@Entity
@Table(name = "candidate_level_language")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CandidateLevelLanguage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "candidateLevelLanguages", allowSetters = true)
    private Candidate candidate;
    
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "candidateLevelLanguages", allowSetters = true)
    private LevelLanguage levelLanguage;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "candidateLevelLanguages", allowSetters = true)
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LevelLanguage getLevelLanguage() {
        return levelLanguage;
    }

    public CandidateLevelLanguage levelLanguage(LevelLanguage levelLanguage) {
        this.levelLanguage = levelLanguage;
        return this;
    }

    public void setLevelLanguage(LevelLanguage levelLanguage) {
        this.levelLanguage = levelLanguage;
    }

    public Language getLanguage() {
        return language;
    }

    public CandidateLevelLanguage language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateLevelLanguage)) {
            return false;
        }
        return id != null && id.equals(((CandidateLevelLanguage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateLevelLanguage{" +
            "id=" + getId() +
            "}";
    }

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
}
