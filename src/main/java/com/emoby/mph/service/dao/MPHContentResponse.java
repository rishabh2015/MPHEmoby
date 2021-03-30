package com.emoby.mph.service.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPHContentResponse {

    @JsonProperty("Document")
	private byte[] data;
    
    @JsonProperty("SPCVUrl")
    private String url;
    
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	


}
