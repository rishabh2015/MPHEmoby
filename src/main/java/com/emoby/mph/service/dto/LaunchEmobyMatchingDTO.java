package com.emoby.mph.service.dto;

import java.util.List;

/**
 * A DTO representing a password change required data - current and new password.
 */
public class LaunchEmobyMatchingDTO {
    private Long jobdescriptionId;
    private List<SectorDTO> sectors;

    public LaunchEmobyMatchingDTO() {
        // Empty constructor needed for Jackson.
    }

    public LaunchEmobyMatchingDTO(Long jobdescriptionId, List<SectorDTO> sectors) {
        this.jobdescriptionId = jobdescriptionId;
        this.sectors = sectors;
    }

	public Long getJobdescriptionId() {
		return jobdescriptionId;
	}

	public void setJobdescriptionId(Long jobdescriptionId) {
		this.jobdescriptionId = jobdescriptionId;
	}

	public List<SectorDTO> getSectors() {
		return sectors;
	}

	public void setSectors(List<SectorDTO> sectors) {
		this.sectors = sectors;
	}

}
