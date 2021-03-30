package com.emoby.mph.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.emoby.mph.domain.SectorSubsector} entity.
 */
public class SectorsubsectorDTO implements Serializable {
    
    private Long id;

    private Long sectorId;

    private String sectorLibelle;

    private Long subsectorId;

    private String subsectorLibelle;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Long getSubsectorId() {
        return subsectorId;
    }

    public void setSubsectorId(Long subsectorId) {
        this.subsectorId = subsectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SectorsubsectorDTO)) {
            return false;
        }

        return id != null && id.equals(((SectorsubsectorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SectorsubsectorDTO{" +
            "id=" + getId() +
            ", sectorId=" + getSectorId() +
            ", subsectorId=" + getSubsectorId() +
            "}";
    }

	public String getSectorLibelle() {
		return sectorLibelle;
	}

	public void setSectorLibelle(String sectorLibelle) {
		this.sectorLibelle = sectorLibelle;
	}

	public String getSubsectorLibelle() {
		return subsectorLibelle;
	}

	public void setSubsectorLibelle(String subsectorLibelle) {
		this.subsectorLibelle = subsectorLibelle;
	}
}
