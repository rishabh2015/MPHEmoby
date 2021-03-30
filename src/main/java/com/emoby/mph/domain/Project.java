package com.emoby.mph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @ManyToOne
    @JsonIgnoreProperties(value = "project_user_relationships", allowSetters = true)
    private User jhi_user;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Jobdescription> jobdescriptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Project code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Project libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public User getJhi_user() {
        return jhi_user;
    }

    public Project jhi_user(User user) {
        this.jhi_user = user;
        return this;
    }

    public void setJhi_user(User user) {
        this.jhi_user = user;
    }

    public Set<Jobdescription> getJobdescriptions() {
        return jobdescriptions;
    }

    public Project jobdescriptions(Set<Jobdescription> jobdescriptions) {
        this.jobdescriptions = jobdescriptions;
        return this;
    }

    public Project addJobdescription(Jobdescription jobdescription) {
        this.jobdescriptions.add(jobdescription);
        jobdescription.setProject(this);
        return this;
    }

    public Project removeJobdescription(Jobdescription jobdescription) {
        this.jobdescriptions.remove(jobdescription);
        jobdescription.setProject(null);
        return this;
    }

    public void setJobdescriptions(Set<Jobdescription> jobdescriptions) {
        this.jobdescriptions = jobdescriptions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
