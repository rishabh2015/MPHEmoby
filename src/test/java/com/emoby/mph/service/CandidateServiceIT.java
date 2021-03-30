package com.emoby.mph.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CandidateServiceIT {

	 @Autowired
	  private WebTestClient webTestClient;
	 
	  public void testGetTodosAPICall() {
			Map<String, String> requestBody = new HashMap<String, String>();
			requestBody.put("Id", "5f03c1d7-43f1-e711-a832-000d3a2a3148");

		  
	    this.webTestClient
	      .post()
	      .uri("https://apicrmforemoby.azurewebsites.net/Services/setActiveCandidate")
	      .contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(requestBody)).exchange()
			.expectBody()      .jsonPath("$.ResponseTitle").isEqualTo("Success");
	  }
	
}
