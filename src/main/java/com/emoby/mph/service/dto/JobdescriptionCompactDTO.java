package com.emoby.mph.service.dto;

import com.emoby.mph.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emoby.mph.domain.Jobdescription} entity.
 */
public class JobdescriptionCompactDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Instant creation_dt;

    private Gender gender;

    @NotNull
    private Long projectId;

    @NotNull
    private String filename;
    
    private int shortListedCount;
    
    private int placedCount;
    
    private Long contentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreation_dt() {
        return creation_dt;
    }

    public void setCreation_dt(Instant creation_dt) {
        this.creation_dt = creation_dt;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobdescriptionCompactDTO)) {
            return false;
        }

        return id != null && id.equals(((JobdescriptionCompactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobdescriptionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creation_dt='" + getCreation_dt() + "'" +
            ", gender='" + getGender() + "'" +
            ", projectId=" + getProjectId() +
            "}";
    }

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getShortListedCount() {
		return shortListedCount;
	}

	public void setShortListedCount(int shortListedCount) {
		this.shortListedCount = shortListedCount;
	}

	public int getPlacedCount() {
		return placedCount;
	}

	public void setPlacedCount(int placedCount) {
		this.placedCount = placedCount;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
}
