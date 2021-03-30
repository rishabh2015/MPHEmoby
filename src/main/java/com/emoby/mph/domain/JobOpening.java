package com.emoby.mph.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A JobOpening.
 */
@Entity
@Table(name = "job_opening")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobOpening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "guid", nullable = false, unique = true)
    private UUID guid;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "text_clean")
    private String text_clean;
    
    @Column(name = "creation_date")
    private Instant creation_date;

    @Column(name = "delete_date")
    private Instant delete_date;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "jobdescription_text")
    private String jobdescription_text;
    
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public JobOpening title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getGuid() {
        return guid;
    }

    public JobOpening guid(UUID guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public String getText_clean() {
        return text_clean;
    }

    public JobOpening text_clean(String text_clean) {
        this.text_clean = text_clean;
        return this;
    }

    public void setText_clean(String text_clean) {
        this.text_clean = text_clean;
    }

    public Instant getCreation_date() {
        return creation_date;
    }

    public JobOpening creation_date(Instant creation_date) {
        this.creation_date = creation_date;
        return this;
    }

    public void setCreation_date(Instant creation_date) {
        this.creation_date = creation_date;
    }

    public Instant getDelete_date() {
        return delete_date;
    }

    public JobOpening delete_date(Instant delete_date) {
        this.delete_date = delete_date;
        return this;
    }

    public void setDelete_date(Instant delete_date) {
        this.delete_date = delete_date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

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
        if (!(o instanceof JobOpening)) {
            return false;
        }
        return id != null && id.equals(((JobOpening) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobOpening{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", guid='" + getGuid() + "'" +
            ", text_clean='" + getText_clean() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            ", delete_date='" + getDelete_date() + "'" +
            "}";
    }
}
