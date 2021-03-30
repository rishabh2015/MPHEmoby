package com.emoby.mph.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emoby.mph.domain.JobOpening} entity.
 */
public class JobOpeningDTO extends JobOpeningCompactDTO implements Serializable {
    
    @Lob
    private String text_clean;

    private Instant creation_date;

    public String getText_clean() {
        return text_clean;
    }

    public void setText_clean(String text_clean) {
        this.text_clean = text_clean;
    }

    public Instant getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Instant creation_date) {
        this.creation_date = creation_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobOpeningDTO)) {
            return false;
        }

        return id != null && id.equals(((JobOpeningDTO) o).id);
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
            ", text_clean='" + getText_clean() + "'" +
            ", file='" + getFile() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            ", delete_date='" + getDelete_date() + "'" +
            "}";
    }
}
