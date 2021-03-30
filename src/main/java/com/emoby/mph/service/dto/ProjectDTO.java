package com.emoby.mph.service.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.emoby.mph.domain.Project} entity.
 */
public class ProjectDTO implements Serializable {
    
	private static final long serialVersionUID = -6328760570268873558L;

	private Long id;

    @NotNull
    private String code;

    @NotNull
    private String libelle;

    private Long jhi_userId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getJhi_userId() {
        return jhi_userId;
    }

    public void setJhi_userId(Long userId) {
        this.jhi_userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        return id != null && id.equals(((ProjectDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", jhi_userId=" + getJhi_userId() +
            "}";
    }
}
