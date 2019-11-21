package com.spring.rabbitmq.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayrollIntegrationPropertiesConfig {
	@Value("${celergoRequest.exchange.name:testExchange}")
	private String celergoRequestExchange;

	@Value("${celergoRequest.queue.name:testQueue}")
	private String celergoRequestQueue;

	@Value("${celergoRequest.routing.key:testKey}")
	private String celergoRequestRoutingKey;

	@Value("${rabbitmq.port:5672}")
	private String rabbitMQPort;

	@Value("${rabbitmq.address:localhost}")
	private String rabbitMQHost;

	@Value("${rabbitmq.userName:guest}")
	private String rabbitMQUserName;

	@Value("${rabbitmq.passwodr:guest}")
	private String rabbitMQPassword;

	/**
	 * @return the rabbitMQPort
	 */
	public String getRabbitMQPort() {
		return rabbitMQPort;
	}

	/**
	 * @param rabbitMQPort the rabbitMQPort to set
	 */
	public void setRabbitMQPort(String rabbitMQPort) {
		this.rabbitMQPort = rabbitMQPort;
	}

	/**
	 * @return the rabbitMQHost
	 */
	public String getRabbitMQHost() {
		return rabbitMQHost;
	}

	/**
	 * @param rabbitMQHost the rabbitMQHost to set
	 */
	public void setRabbitMQHost(String rabbitMQHost) {
		this.rabbitMQHost = rabbitMQHost;
	}

	/**
	 * @return the rabbitMQUserName
	 */
	public String getRabbitMQUserName() {
		return rabbitMQUserName;
	}

	/**
	 * @param rabbitMQUserName the rabbitMQUserName to set
	 */
	public void setRabbitMQUserName(String rabbitMQUserName) {
		this.rabbitMQUserName = rabbitMQUserName;
	}

	/**
	 * @return the rabbitMQPassword
	 */
	public String getRabbitMQPassword() {
		return rabbitMQPassword;
	}

	/**
	 * @param rabbitMQPassword the rabbitMQPassword to set
	 */
	public void setRabbitMQPassword(String rabbitMQPassword) {
		this.rabbitMQPassword = rabbitMQPassword;
	}

	/**
	 * @return the celergoRequesteExchange
	 */
	public String getCelergoRequestExchange() {
		return celergoRequestExchange;
	}

	/**
	 * @param celergoRequesteExchange the celergoRequesteExchange to set
	 */
	public void setCelergoRequestExchange(String celergoRequestExchange) {
		this.celergoRequestExchange = celergoRequestExchange;
	}

	/**
	 * @return the celergoRequestQueue
	 */
	public String getCelergoRequestQueue() {
		return celergoRequestQueue;
	}

	/**
	 * @param celergoRequestQueue the celergoRequestQueue to set
	 */
	public void setCelergoRequestQueue(String celergoRequestQueue) {
		this.celergoRequestQueue = celergoRequestQueue;
	}

	/**
	 * @return the celergoRequestRoutingKey
	 */
	public String getCelergoRequestRoutingKey() {
		return celergoRequestRoutingKey;
	}

	/**
	 * @param celergoRequestRoutingKey the celergoRequestRoutingKey to set
	 */
	public void setCelergoRequestRoutingKey(String celergoRequestRoutingKey) {
		this.celergoRequestRoutingKey = celergoRequestRoutingKey;
	}

	
}
