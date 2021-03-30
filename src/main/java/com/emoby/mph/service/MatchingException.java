package com.emoby.mph.service;

import org.springframework.web.reactive.function.client.WebClientException;

public class MatchingException extends WebClientException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4716823832372247057L;

	public MatchingException(String message) {
		super(message);
	}

	public MatchingException(String message, Throwable t) {
		super(message, t);
	}

}
