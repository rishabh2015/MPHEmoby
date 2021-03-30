package com.emoby.mph.service;

import org.springframework.web.reactive.function.client.WebClientException;

public class MphDynamicsException extends WebClientException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4716823832372247057L;

	public MphDynamicsException(String message) {
		super(message);
	}

	public MphDynamicsException(String message, Throwable t) {
		super(message, t);
	}

}
