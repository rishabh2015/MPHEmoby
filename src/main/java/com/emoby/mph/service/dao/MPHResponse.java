package com.emoby.mph.service.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MPHResponse {

    @JsonProperty("Code")
	private Integer code;
	
    @JsonProperty("ResponseTitle")
	private String responseTitle;
    
    @JsonProperty("ResponseDescriptionEn")
	private String responseDescriptionEn;
    
    
    @JsonProperty("ResponseDescriptionAr")
	private String responseDescriptionAr;
    
    @JsonProperty("ContentType")
    private String contentType;
    
    @JsonProperty("Content")
	private MPHContentResponse content;
    
    @JsonProperty("validationCode")
	private Integer validationCode;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getResponseDescriptionEn() {
		return responseDescriptionEn;
	}

	public void setResponseDescriptionEn(String responseDescriptionEn) {
		this.responseDescriptionEn = responseDescriptionEn;
	}

	public String getResponseDescriptionAr() {
		return responseDescriptionAr;
	}

	public void setResponseDescriptionAr(String responseDescriptionAr) {
		this.responseDescriptionAr = responseDescriptionAr;
	}

	public Integer getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(Integer validationCode) {
		this.validationCode = validationCode;
	}

	public String getResponseTitle() {
		return responseTitle;
	}

	public void setResponseTitle(String responseTitle) {
		this.responseTitle = responseTitle;
	}

	public MPHContentResponse getContent() {
		return content;
	}

	public void setContent(MPHContentResponse content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}



}
