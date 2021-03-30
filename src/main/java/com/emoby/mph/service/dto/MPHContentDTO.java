package com.emoby.mph.service.dto;

import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emoby.mph.domain.Content} entity.
 */
public class MPHContentDTO implements Serializable {
    
    private byte[] data;

    private String dataContentType;

    private String filename;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDataContentType() {
		return dataContentType;
	}

	public void setDataContentType(String dataContentType) {
		this.dataContentType = dataContentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
