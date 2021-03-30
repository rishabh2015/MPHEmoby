package com.emoby.mph.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.emoby.mph.domain.ProjectphaseActivity} entity.
 */
public class ProjectphaseActivityDTO implements Serializable {
    
    private Long id;


    private Long projectphaseId;

    private String projectphaseLibelle;

    private Long activityId;

    private String activityLibelle;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectphaseId() {
        return projectphaseId;
    }

    public void setProjectphaseId(Long projectphaseId) {
        this.projectphaseId = projectphaseId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectphaseActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((ProjectphaseActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectphaseActivityDTO{" +
            "id=" + getId() +
            ", projectphaseId=" + getProjectphaseId() +
            ", activityId=" + getActivityId() +
            "}";
    }

	public String getProjectphaseLibelle() {
		return projectphaseLibelle;
	}

	public void setProjectphaseLibelle(String projectphaseLibelle) {
		this.projectphaseLibelle = projectphaseLibelle;
	}

	public String getActivityLibelle() {
		return activityLibelle;
	}

	public void setActivityLibelle(String activityLibelle) {
		this.activityLibelle = activityLibelle;
	}
}
