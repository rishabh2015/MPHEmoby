package com.emoby.mph.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.emoby.mph.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Jobdescription.
 */
@Entity
@Table(name = "jobdescription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Jobdescription implements Serializable {

    public Jobdescription() {
		super();
	}

	public Jobdescription(Long id) {
		super();
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "creation_dt", nullable = false)
    private Instant creation_dt;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "jobdescriptions", allowSetters = true)
    private Project project;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_nationalities",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nationalities_id", referencedColumnName = "id"))
    private Set<Country> nationalities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_locations",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "locations_id", referencedColumnName = "id"))
    private Set<Country> locations = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_educationlevel",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "educationlevel_id", referencedColumnName = "id"))
    private Set<Educationlevel> educationlevels = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_projectphase_activity",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "projectphase_activity_id", referencedColumnName = "id"))
    private Set<ProjectphaseActivity> projectphaseActivities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_technical_discipline",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "technical_discipline_id", referencedColumnName = "id"))
    private Set<TechnicalDiscipline> technicalDisciplines = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_experience",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "experience_id", referencedColumnName = "id"))
    private Set<Experience> experiences = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_language",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))
    private Set<Language> languages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "jobdescription_sector",
               joinColumns = @JoinColumn(name = "jobdescription_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sector_id", referencedColumnName = "id"))
    private Set<Sector> sectors = new HashSet<>();

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Content content;

    @Column(name = "content_id", nullable = true, insertable =false, updatable=false)
    private Long contentId;
    
	@OneToMany(mappedBy = "jobdescription", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<PotentialCandidate> potentialCandidates = new HashSet<>();
    
	
	@OneToMany(mappedBy = "shortlisted", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Set<Candidate> candidateShortlisted = new HashSet<>();
	
	@OneToMany(mappedBy = "placed", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Set<Candidate> candidatePlaced = new HashSet<>();
	
    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Jobdescription name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreation_dt() {
        return creation_dt;
    }

    public Jobdescription creation_dt(Instant creation_dt) {
        this.creation_dt = creation_dt;
        return this;
    }

    public void setCreation_dt(Instant creation_dt) {
        this.creation_dt = creation_dt;
    }

    public Gender getGender() {
        return gender;
    }

    public Jobdescription gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFilename() {
        return filename;
    }

    public Jobdescription filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Project getProject() {
        return project;
    }

    public Jobdescription project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Country> getNationalities() {
        return nationalities;
    }

    public Jobdescription nationalities(Set<Country> countries) {
        this.nationalities = countries;
        return this;
    }

    public Jobdescription addNationalities(Country country) {
        this.nationalities.add(country);
        return this;
    }

    public Jobdescription removeNationalities(Country country) {
        this.nationalities.remove(country);
        return this;
    }

    public void setNationalities(Set<Country> countries) {
        this.nationalities = countries;
    }

    public Set<Country> getLocations() {
        return locations;
    }

    public Jobdescription locations(Set<Country> countries) {
        this.locations = countries;
        return this;
    }

    public Jobdescription addLocations(Country country) {
        this.locations.add(country);
        return this;
    }

    public Jobdescription removeLocations(Country country) {
        this.locations.remove(country);
        return this;
    }

    public void setLocations(Set<Country> countries) {
        this.locations = countries;
    }

    public Set<Educationlevel> getEducationlevels() {
        return educationlevels;
    }

    public Jobdescription educationlevels(Set<Educationlevel> educationlevels) {
        this.educationlevels = educationlevels;
        return this;
    }

    public Jobdescription addEducationlevel(Educationlevel educationlevel) {
        this.educationlevels.add(educationlevel);
        return this;
    }

    public Jobdescription removeEducationlevel(Educationlevel educationlevel) {
        this.educationlevels.remove(educationlevel);
        return this;
    }

    public void setEducationlevels(Set<Educationlevel> educationlevels) {
        this.educationlevels = educationlevels;
    }

    public Set<ProjectphaseActivity> getProjectphaseActivities() {
        return projectphaseActivities;
    }

    public Jobdescription projectphaseActivities(Set<ProjectphaseActivity> projectphaseActivities) {
        this.projectphaseActivities = projectphaseActivities;
        return this;
    }

    public Jobdescription addProjectphaseActivity(ProjectphaseActivity projectphaseActivity) {
        this.projectphaseActivities.add(projectphaseActivity);
        return this;
    }

    public Jobdescription removeProjectphaseActivity(ProjectphaseActivity projectphaseActivity) {
        this.projectphaseActivities.remove(projectphaseActivity);
        return this;
    }

    public void setProjectphaseActivities(Set<ProjectphaseActivity> projectphaseActivities) {
        this.projectphaseActivities = projectphaseActivities;
    }

    public Set<TechnicalDiscipline> getTechnicalDisciplines() {
        return technicalDisciplines;
    }

    public Jobdescription technicalDisciplines(Set<TechnicalDiscipline> technicalDisciplines) {
        this.technicalDisciplines = technicalDisciplines;
        return this;
    }

    public Jobdescription addTechnicalDiscipline(TechnicalDiscipline technicalDiscipline) {
        this.technicalDisciplines.add(technicalDiscipline);
        return this;
    }

    public Jobdescription removeTechnicalDiscipline(TechnicalDiscipline technicalDiscipline) {
        this.technicalDisciplines.remove(technicalDiscipline);
        return this;
    }

    public void setTechnicalDisciplines(Set<TechnicalDiscipline> technicalDisciplines) {
        this.technicalDisciplines = technicalDisciplines;
    }

    public Set<Experience> getExperiences() {
        return experiences;
    }

    public Jobdescription experiences(Set<Experience> experiences) {
        this.experiences = experiences;
        return this;
    }

    public Jobdescription addExperience(Experience experience) {
        this.experiences.add(experience);
        return this;
    }

    public Jobdescription removeExperience(Experience experience) {
        this.experiences.remove(experience);
        return this;
    }

    public void setExperiences(Set<Experience> experiences) {
        this.experiences = experiences;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public Jobdescription languages(Set<Language> languages) {
        this.languages = languages;
        return this;
    }

    public Jobdescription addLanguage(Language language) {
        this.languages.add(language);
        return this;
    }

    public Jobdescription removeLanguage(Language language) {
        this.languages.remove(language);
        return this;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<Sector> getSectors() {
        return sectors;
    }

    public Jobdescription sectors(Set<Sector> sectors) {
        this.sectors = sectors;
        return this;
    }

    public Jobdescription addSector(Sector sector) {
        this.sectors.add(sector);
        return this;
    }

    public Jobdescription removeSector(Sector sector) {
        this.sectors.remove(sector);
        return this;
    }

    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
    }

    public Content getContent() {
        return content;
    }

    public Jobdescription content(Content content) {
        this.content = content;
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Jobdescription)) {
            return false;
        }
        return id != null && id.equals(((Jobdescription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Jobdescription{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", creation_dt='" + getCreation_dt() + "'" +
            ", gender='" + getGender() + "'" +
            ", filename='" + getFilename() + "'" +
            "}";
    }

	public Set<PotentialCandidate> getPotentialCandidates() {
		return potentialCandidates;
	}

	public void setPotentialCandidates(Set<PotentialCandidate> potentialCandidates) {
		this.potentialCandidates = potentialCandidates;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	
	@PreRemove
	private void preRemove() {
	   candidateShortlisted.forEach( candidate -> candidate.setShortlisted(null));
	   candidatePlaced.forEach( candidate -> candidate.setPlaced(null));
	}
}
