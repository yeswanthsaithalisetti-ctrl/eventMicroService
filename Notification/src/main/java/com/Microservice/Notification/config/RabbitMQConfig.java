package com.Microservice.Notification.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;


@Configuration
public class RabbitMQConfig {

	public static final String emailQueue = "msEmail.queue";
	public static final String dlQueue = "msDl.queue";
	
	public static final String emailExchange = "msEmail.exchange";
	public static final String dlExchange = "msDl.exchange";
	
	public static final String emailRoutingKey = "msEmail.routingkey";
	public static final String dlRoutingKey = "msDl.routingkey";
	
	@Bean
	public DirectExchange emailExchange() {
		return  new DirectExchange(emailExchange, true, false);
	}
	
	@Bean
	public DirectExchange dlExchange() {
		return  new DirectExchange(dlExchange, true, false);
	}
	
	@Bean
	public Queue emailQueue() {
		return QueueBuilder.durable(emailQueue)
				.withArgument("x-dead-letter-exchange",dlExchange)
				.withArgument("x-dead-letter-routing-key",dlRoutingKey)
				.withArgument("x-message-ttl",300000)
				.withArgument("x-max-length",10000).build();
	}
	
	@Bean
	public Queue dlQueue() {
		return QueueBuilder.durable(dlQueue).build();
	}
	
	@Bean
	public Binding emailBinding() {
		return BindingBuilder.bind(emailQueue())
				.to(emailExchange()).with(emailRoutingKey);
	}
	
	@Bean
	public Binding dlBinding() {
		return BindingBuilder.bind(dlQueue())
				.to(dlExchange()).with(dlRoutingKey);
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	
}

