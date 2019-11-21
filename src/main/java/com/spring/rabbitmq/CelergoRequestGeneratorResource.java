package com.spring.rabbitmq;


import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.rabbitmq.service.CelergoRequestGeneratorService;

@RestController
@RequestMapping("/send")
public class CelergoRequestGeneratorResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(CelergoRequestGeneratorResource.class);

	@Autowired
	private CelergoRequestGeneratorService celergoRequestGeneratorService;

	final ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * API to generate the response for Celergo by using the transformed data and
	 * place it into the queue. This end point is provided only for testing purpose.
	 * 
	 * @param transformedData
	 * @param partnerTemplate
	 * @return 
	 * 
	 * @return response
	 */
	@PostMapping(value = "/message", consumes = "text/plain")
	public ResponseEntity<Object> generateRequestFromTransformedData(
			@Valid @RequestBody String msg) {

		LOGGER.info("Request received to generate the celergo api response from the transformed data ");
		celergoRequestGeneratorService.generateCelergoRequest(msg);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}