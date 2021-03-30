package com.emoby.mph.service.dto;

import java.io.Serializable;

import javax.persistence.Lob;

/**
 * A DTO for the {@link com.emoby.mph.domain.Candidate} entity.
 */
public class CandidateCommentDTO implements Serializable {
    
    protected Long id;

    @Lob
    private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
