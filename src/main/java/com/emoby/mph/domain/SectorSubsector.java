package com.emoby.mph.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Sectorsubsector.
 */
@Entity
@Table(name = "sector_subsector")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SectorSubsector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "sectorsubsectors", allowSetters = true)
    private Sector sector;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "sectorsubsectors", allowSetters = true)
    private Subsector subsector;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sector getSector() {
        return sector;
    }

    public SectorSubsector sector(Sector sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Subsector getSubsector() {
        return subsector;
    }

    public SectorSubsector subsector(Subsector subsector) {
        this.subsector = subsector;
        return this;
    }

    public void setSubsector(Subsector subsector) {
        this.subsector = subsector;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SectorSubsector)) {
            return false;
        }
        return id != null && id.equals(((SectorSubsector) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sectorsubsector{" +
            "id=" + getId() +
            "}";
    }
}
