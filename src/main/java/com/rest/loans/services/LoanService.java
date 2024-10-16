package com.rest.loans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rest.loans.dtos.InterestRate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class LoanService {

	@Autowired
	private RestTemplate restTemplate;

	private static final String SERVICE_NAME = "loan-service";

	private static final String RATE_SERVICE_URL = "http://3.110.184.96:9000/rates/{type}";

	@CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "getDefaultLoan")
	public InterestRate getAllLoansByType(String type) {
		System.out.println("****** original method called..... *****");
		ResponseEntity<InterestRate> response = restTemplate.getForEntity(RATE_SERVICE_URL, InterestRate.class, type);
		return response.getBody();
	}

	public InterestRate getDefaultLoan(Exception e) {
		System.out.println("***** fallback method called... *******");
		return new InterestRate();
	}
}
