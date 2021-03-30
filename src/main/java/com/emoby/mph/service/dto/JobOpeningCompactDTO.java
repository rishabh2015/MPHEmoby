package com.emoby.mph.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emoby.mph.domain.JobOpening} entity.
 */
public class JobOpeningCompactDTO implements Serializable {
    
	private static final long serialVersionUID = -4634654045836429165L;

	protected Long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private UUID guid;

    @Lob
    private byte[] file;

    private String fileContentType;

    private Instant delete_date;

    private String jobdescription_text;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public Instant getDelete_date() {
        return delete_date;
    }

    public void setDelete_date(Instant delete_date) {
        this.delete_date = delete_date;
    }

    public String getJobdescription_text() {
		return jobdescription_text;
	}

	public void setJobdescription_text(String jobdescription_text) {
		this.jobdescription_text = jobdescription_text;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobOpeningCompactDTO)) {
            return false;
        }

        return id != null && id.equals(((JobOpeningCompactDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobOpeningDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", guid='" + getGuid() + "'" +
            ", file='" + getFile() + "'" +
            ", delete_date='" + getDelete_date() + "'" +
            "}";
    }
}
