package com.emoby.mph.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.emoby.mph.config.ApplicationProperties;
import com.google.common.io.Files;

import reactor.core.publisher.Mono;

@Service
public class TextCleanService {

	private ApplicationProperties applicationProperties;
	
	private final WebClient webClient;
	
	private final Logger log = LoggerFactory.getLogger(TextCleanService.class);
	
    public TextCleanService(ApplicationProperties applicationProperties) {
    	this.applicationProperties = applicationProperties;
    	this.webClient = WebClient.builder().baseUrl(applicationProperties.getEmobyMPHServiceURL()).build();
    }
	
	protected Mono<String> callTextClean(byte[] myByteArray, File tempFile) {
	    log.debug("Starting callGetTextClean");
	    try {
			Files.write(myByteArray, tempFile);
		} catch (IOException e) {
            throw new MatchingException(e.getMessage());
		}

		Mono<String> textCleanFlux = webClient.post()
	      .uri("/{url}","getTextClean")
	      .accept( MediaType.APPLICATION_JSON )                
	      .contentType(MediaType.MULTIPART_FORM_DATA)
          .body(BodyInserters.fromMultipartData(fromFile(tempFile)))
	      .retrieve()
	      .bodyToMono(String.class)
	      .doOnError(error -> {
              throw new MatchingException(error.getMessage());
	      });
	 
	    return textCleanFlux;
	}

    public MultiValueMap<String, HttpEntity<?>> fromFile(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(file));
        return builder.build();
    }
	

	public ApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
}
