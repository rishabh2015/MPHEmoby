package com.emoby.mph.service;

public class NoCandidateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NoCandidateException() {
        super("No Candidate Exception");
    }
}
