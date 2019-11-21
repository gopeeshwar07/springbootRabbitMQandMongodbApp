package com.spring.rabbitmq.service;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.rabbitmq.client.Channel;

@Service
public class CelergoRequestQueueHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(CelergoRequestQueueHandler.class);

	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * Place the generated celergoRequest on to the CelergoRequestQueue
	 * 
	 * @param rabbitTemplate
	 * @param exchange
	 * @param routingKey
	 * @param generatedCelergoRequest
	 */
	public void publishCelergoRequest(String exchange, String routingKey, String message) {
		try {
			rabbitTemplate.convertAndSend(exchange, routingKey, message);
			LOGGER.info("Celergo Request is placed on to the CelergoRequestQueue");
		} catch (AmqpException ae) {
			LOGGER.error("Exception occurred while placing the data on to the queue is :{}", ae.getMessage());
			throw ae;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while placing the data on to the queue is :{}", e.getMessage());
			throw e;
		}
	}

	/**
	 * Rabbit MQ listener to consume the celergoRequest from the CelergoRequestQueue
	 * 
	 * @param generatedCelergoRequest to be consumed from the Queue
	 * @param Channel
	 * @Header
	 * @return acknowledgement
	 */
	@RabbitListener(queues = "${celergoRequest.queue.name}",containerFactory = "prefetchOneRabbitListenerContainerFactory")
	public void consumeCelergoRequest(final String msg, Channel channel,
			@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		try {
			LOGGER.info("Received generated request from CelergoRequestQueue is {}", msg);
			// Received the response from Celergo and can be used for reverse transformation
			channel.basicAck(tag, false);
			return;
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND || ex.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE
					|| ex.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
				channel.basicNack(tag, false, true);
			} else {
				LOGGER.error("Exception while consuming the request from Queue and Invoking the Celergo API is :{}",
						ex.getMessage());
				if (channel.isOpen())
					channel.basicAck(tag, false);
			}
		} catch (Exception e) {
			LOGGER.error("Exception while consuming the request from Queue and Invoking the Celergo API is :{}",
					e.getMessage());
			if (channel.isOpen()) {
				channel.basicAck(tag, false);
			}


		} finally {
			try {
				channel.close();
			} catch (TimeoutException e) {
				LOGGER.error("Closing channel");
				e.printStackTrace();
			}
		}
	}
}