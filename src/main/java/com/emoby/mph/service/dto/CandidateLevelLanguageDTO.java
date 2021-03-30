package com.emoby.mph.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.emoby.mph.domain.CandidateLevelLanguage} entity.
 */
public class CandidateLevelLanguageDTO implements Serializable {
    
    private Long id;

    private Integer candidateId;

    private LevelLanguageDTO levelLanguage;

    private LanguageDTO language;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidateLevelLanguageDTO)) {
            return false;
        }

        return id != null && id.equals(((CandidateLevelLanguageDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidateLevelLanguageDTO{" +
            "id=" + getId() +
            ", candidate=" + getCandidateId() +
            ", levelLanguage=" + getLevelLanguage() +
            ", language=" + getLanguage() +
            "}";
    }


	public LevelLanguageDTO getLevelLanguage() {
		return levelLanguage;
	}

	public void setLevelLanguage(LevelLanguageDTO levelLanguage) {
		this.levelLanguage = levelLanguage;
	}

	public LanguageDTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageDTO language) {
		this.language = language;
	}

	public Integer getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}

}
