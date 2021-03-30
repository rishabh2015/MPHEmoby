package com.emoby.mph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ProjectphaseActivity.
 */
@Entity
@Table(name = "projectphase_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProjectphaseActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "projectphaseActivities", allowSetters = true)
    private Projectphase projectphase;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "projectphaseActivities", allowSetters = true)
    private Activity activity;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projectphase getProjectphase() {
        return projectphase;
    }

    public ProjectphaseActivity projectphase(Projectphase projectphase) {
        this.projectphase = projectphase;
        return this;
    }

    public void setProjectphase(Projectphase projectphase) {
        this.projectphase = projectphase;
    }

    public Activity getActivity() {
        return activity;
    }

    public ProjectphaseActivity activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectphaseActivity)) {
            return false;
        }
        return id != null && id.equals(((ProjectphaseActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectphaseActivity{" +
            "id=" + getId() +
            "}";
    }
}
