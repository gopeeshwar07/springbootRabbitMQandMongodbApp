package com.spring.rabbitmq.config;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;


@EnableRabbit
@Configuration
public class QueueConfig implements RabbitListenerConfigurer {

	CachingConnectionFactory connectionFactory = null;
	@Autowired
	public PayrollIntegrationPropertiesConfig payrollIntegrationPropertiesConfig;

//	@Autowired
//	RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

	/** Configure Exchange for CelergoRequestQueue */
	@Bean
	public DirectExchange getCelergoRequestExchange() {
		return new DirectExchange(payrollIntegrationPropertiesConfig.getCelergoRequestExchange());
	}

	/** Configure CelergoRequestQueue */
	@Bean
	public Queue getCelergoRequestQueue() {
		return new Queue(payrollIntegrationPropertiesConfig.getCelergoRequestQueue());
	}

	/**
	 * Binding CelergoRequestExchange and CelergoRequestQueue using
	 * CelergoRequestRoutingKey
	 */
	@Bean
	public Binding declareBindingApp() {
		return BindingBuilder.bind(getCelergoRequestQueue()).to(getCelergoRequestExchange())
				.with(payrollIntegrationPropertiesConfig.getCelergoRequestRoutingKey());
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		connectionFactory = new CachingConnectionFactory(payrollIntegrationPropertiesConfig.getRabbitMQHost(),
				Integer.valueOf(payrollIntegrationPropertiesConfig.getRabbitMQPort()));
		connectionFactory.setUsername(payrollIntegrationPropertiesConfig.getRabbitMQUserName());
		connectionFactory.setPassword(payrollIntegrationPropertiesConfig.getRabbitMQPassword());

//		rabbitListenerEndpointRegistry.stop();
//		connectionFactory.resetConnection();
//
//		rabbitListenerEndpointRegistry.start();
		return connectionFactory;
	}

	/** Bean for RabbitTemplate */
	@Bean
	public RabbitTemplate rabbitTemplate() {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}

	/**
	 * Setting consumers ack mode to manual and prefetch count to one to take one
	 * message at a time in synchronous way
	 */
	@Bean
	public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchOneRabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPrefetchCount(1);
		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		factory.setMissingQueuesFatal(false);
		return factory;
	}
}
