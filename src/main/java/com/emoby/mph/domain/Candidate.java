package com.emoby.mph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.emoby.mph.domain.enumeration.Gender;

/**
 * A Candidate.
 */
@Entity
@Table(name = "candidate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Candidate implements Serializable {

    public Candidate() {
		super();
	}

	public Candidate(Long id) {
		super();
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate date_of_birth;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "text_clean")
    private String text_clean;

    @NotNull
    @Column(name = "guid", nullable = false, unique = true)
    private UUID guid;

    @Column(name = "creation_date")
    private Instant creation_date;

    @Column(name = "update_date")
    private Instant update_date;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "comment")
    private String comment;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone_number;
    
    @ManyToOne(optional = true)
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Country nationality;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "candidate_dual_nationality",
               joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "dual_nationality_id", referencedColumnName = "id"))
    private Set<Country> dualNationalities = new HashSet<>();

    @ManyToOne(optional = true)
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Country location;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Educationlevel educationlevel;

    @ManyToOne(optional = true)
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Experience experience;

    @ManyToOne
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private MobyStatus mobyStatus;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "candidate_sector_subsector",
               joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sector_subsector_id", referencedColumnName = "id"))
    private Set<SectorSubsector> sectorSubsectors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "candidate_projectphase_activity",
               joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "projectphase_activity_id", referencedColumnName = "id"))
    private Set<ProjectphaseActivity> projectphaseActivities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "candidate_technical_discipline",
               joinColumns = @JoinColumn(name = "candidate_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "technical_discipline_id", referencedColumnName = "id"))
    private Set<TechnicalDiscipline> technicalDisciplines = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Jobdescription shortlisted;

    @ManyToOne
    @JsonIgnoreProperties(value = "candidates", allowSetters = true)
    private Jobdescription placed;

    @OneToMany(mappedBy = "candidate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<CandidateLevelLanguage> levelLanguages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public Candidate gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public Candidate last_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public Candidate first_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public Candidate date_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
        return this;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getText_clean() {
        return text_clean;
    }

    public Candidate text_clean(String text_clean) {
        this.text_clean = text_clean;
        return this;
    }

    public void setText_clean(String text_clean) {
        this.text_clean = text_clean;
    }

    public UUID getGuid() {
        return guid;
    }

    public Candidate guid(UUID guid) {
        this.guid = guid;
        return this;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public Instant getCreation_date() {
        return creation_date;
    }

    public Candidate creation_date(Instant creation_date) {
        this.creation_date = creation_date;
        return this;
    }

    public void setCreation_date(Instant creation_date) {
        this.creation_date = creation_date;
    }

    public Instant getUpdate_date() {
        return update_date;
    }

    public Candidate update_date(Instant update_date) {
        this.update_date = update_date;
        return this;
    }

    public void setUpdate_date(Instant update_date) {
        this.update_date = update_date;
    }

    public String getComment() {
        return comment;
    }

    public Candidate comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public Candidate email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Candidate phone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }
    
    public Country getNationality() {
        return nationality;
    }

    public Candidate nationality(Country country) {
        this.nationality = country;
        return this;
    }

    public void setNationality(Country country) {
        this.nationality = country;
    }

    public Set<Country> getDualNationalities() {
        return dualNationalities;
    }

    public Candidate dualNationalities(Set<Country> countries) {
        this.dualNationalities = countries;
        return this;
    }

    public Candidate addDualNationality(Country country) {
        this.dualNationalities.add(country);
        return this;
    }

    public Candidate removeDualNationality(Country country) {
        this.dualNationalities.remove(country);
        return this;
    }

    public void setDualNationalities(Set<Country> countries) {
        this.dualNationalities = countries;
    }

    public Country getLocation() {
        return location;
    }

    public Candidate location(Country country) {
        this.location = country;
        return this;
    }

    public void setLocation(Country country) {
        this.location = country;
    }

    public Educationlevel getEducationlevel() {
        return educationlevel;
    }

    public Candidate educationlevel(Educationlevel educationlevel) {
        this.educationlevel = educationlevel;
        return this;
    }

    public void setEducationlevel(Educationlevel educationlevel) {
        this.educationlevel = educationlevel;
    }

    public Experience getExperience() {
        return experience;
    }

    public Candidate experience(Experience experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public MobyStatus getMobyStatus() {
        return mobyStatus;
    }

    public Candidate mobyStatus(MobyStatus mobyStatus) {
        this.mobyStatus = mobyStatus;
        return this;
    }

    public void setMobyStatus(MobyStatus mobyStatus) {
        this.mobyStatus = mobyStatus;
    }
    
    public Set<SectorSubsector> getSectorSubsectors() {
        return sectorSubsectors;
    }

    public Candidate sectorSubsectors(Set<SectorSubsector> sectorSubsectors) {
        this.sectorSubsectors = sectorSubsectors;
        return this;
    }

    public Candidate addSectorSubsector(SectorSubsector sectorSubsector) {
        this.sectorSubsectors.add(sectorSubsector);
        return this;
    }

    public Candidate removeSectorSubsector(SectorSubsector sectorSubsector) {
        this.sectorSubsectors.remove(sectorSubsector);
        return this;
    }

    public void setSectorSubsectors(Set<SectorSubsector> sectorSubsectors) {
        this.sectorSubsectors = sectorSubsectors;
    }

    public Set<ProjectphaseActivity> getProjectphaseActivities() {
        return projectphaseActivities;
    }

    public Candidate projectphaseActivities(Set<ProjectphaseActivity> projectphaseActivities) {
        this.projectphaseActivities = projectphaseActivities;
        return this;
    }

    public Candidate addProjectphaseActivity(ProjectphaseActivity projectphaseActivity) {
        this.projectphaseActivities.add(projectphaseActivity);
        return this;
    }

    public Candidate removeProjectphaseActivity(ProjectphaseActivity projectphaseActivity) {
        this.projectphaseActivities.remove(projectphaseActivity);
        return this;
    }

    public void setProjectphaseActivities(Set<ProjectphaseActivity> projectphaseActivities) {
        this.projectphaseActivities = projectphaseActivities;
    }

    public Set<TechnicalDiscipline> getTechnicalDisciplines() {
        return technicalDisciplines;
    }

    public Candidate technicalDisciplines(Set<TechnicalDiscipline> technicalDisciplines) {
        this.technicalDisciplines = technicalDisciplines;
        return this;
    }

    public Candidate addTechnicalDiscipline(TechnicalDiscipline technicalDiscipline) {
        this.technicalDisciplines.add(technicalDiscipline);
        return this;
    }

    public Candidate removeTechnicalDiscipline(TechnicalDiscipline technicalDiscipline) {
        this.technicalDisciplines.remove(technicalDiscipline);
        return this;
    }

    public void setTechnicalDisciplines(Set<TechnicalDiscipline> technicalDisciplines) {
        this.technicalDisciplines = technicalDisciplines;
    }

    public Jobdescription getShortlisted() {
        return shortlisted;
    }

    public Candidate shortlisted(Jobdescription jobdescription) {
        this.shortlisted = jobdescription;
        return this;
    }

    public void setShortlisted(Jobdescription jobdescription) {
        this.shortlisted = jobdescription;
    }

    public Jobdescription getPlaced() {
        return placed;
    }

    public Candidate placed(Jobdescription jobdescription) {
        this.placed = jobdescription;
        return this;
    }

    public void setPlaced(Jobdescription jobdescription) {
        this.placed = jobdescription;
    }

    public Set<CandidateLevelLanguage> getLevelLanguages() {
        return levelLanguages;
    }

    public Candidate levelLanguages(Set<CandidateLevelLanguage> candidateLevelLanguages) {
        this.levelLanguages = candidateLevelLanguages;
        return this;
    }

    public Candidate addLevelLanguage(CandidateLevelLanguage candidateLevelLanguage) {
        this.levelLanguages.add(candidateLevelLanguage);
        return this;
    }

    public Candidate removeLevelLanguage(CandidateLevelLanguage candidateLevelLanguage) {
        this.levelLanguages.remove(candidateLevelLanguage);
        return this;
    }

    public void setLevelLanguages(Set<CandidateLevelLanguage> candidateLevelLanguages) {
        this.levelLanguages = candidateLevelLanguages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidate)) {
            return false;
        }
        return id != null && id.equals(((Candidate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidate{" +
            "id=" + getId() +
            ", gender='" + getGender() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", date_of_birth='" + getDate_of_birth() + "'" +
            ", text_clean='" + getText_clean() + "'" +
            ", guid='" + getGuid() + "'" +
            ", creation_date='" + getCreation_date() + "'" +
            ", update_date='" + getUpdate_date() + "'" +
            ", comment='" + getComment() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone_number='" + getPhone_number() + "'" +
            "}";
    }
}
