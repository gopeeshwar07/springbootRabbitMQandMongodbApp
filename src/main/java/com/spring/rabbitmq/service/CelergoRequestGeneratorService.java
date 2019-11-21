package com.spring.rabbitmq.service;



import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.rabbitmq.config.PayrollIntegrationPropertiesConfig;

@Service
public class CelergoRequestGeneratorService{
	private static final Logger LOGGER = LoggerFactory.getLogger(CelergoRequestGeneratorService.class);

	@Autowired
	private PayrollIntegrationPropertiesConfig payrollIntegrationPropertiesConfig;

	@Autowired
	private CelergoRequestQueueHandler celergoRequestQueueHandler;

	/**
	 * Generates the Celergo request payload from the received transformed data
	 * 
	 * @param partnerTemplate
	 * @param transformedData
	 * 
	 * @return generatedRessponse
	 */
	public void generateCelergoRequest(final String messageToQueue){
		
				String exchange = payrollIntegrationPropertiesConfig.getCelergoRequestExchange();
				String routingKey = payrollIntegrationPropertiesConfig.getCelergoRequestRoutingKey();
				/* Placing the generated response on to the RabbitMQ */
				
				celergoRequestQueueHandler.publishCelergoRequest(exchange, routingKey, messageToQueue);
				LOGGER.info("Message placed on to the queue successfully is :{}",messageToQueue);
	}

}
