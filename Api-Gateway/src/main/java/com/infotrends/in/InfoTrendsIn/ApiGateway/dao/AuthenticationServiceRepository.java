package com.infotrends.in.InfoTrendsIn.ApiGateway.dao;

import com.infotrends.in.InfoTrendsIn.ApiGateway.model.ConnValidationResponse;
import com.infotrends.in.InfoTrendsIn.exceptions.BusinessException;
import com.infotrends.in.InfoTrendsIn.security.SecurityConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class AuthenticationServiceRepository {

//	@GetMapping(value = "/api/v1/validateConnection", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ConnValidationResponse validateConnection(String authToken) {

		try {

			WebClient client = WebClient.builder().baseUrl("lb://authentication-service").build();
			ConnValidationResponse response = client.get().uri("/api/v1/validateConnection")
					.header(SecurityConstants.HEADER, authToken)
					.retrieve().toEntity(ConnValidationResponse.class).flux().blockFirst().getBody();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new BusinessException("", HttpStatus.BAD_GATEWAY, "Gateway Error");
	}

}
