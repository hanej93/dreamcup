package com.dreamcup.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

	private final ObjectMapper objectMapper;

	public static final String CHAT_EXCHANGE_NAME = "chat.exchange";
	public static final String MESSAGE_ROUTING_KEY = "message.*";
	public static final String MESSAGE_QUEUE_NAME = "message.queue";

	@Bean
	public Queue messageQueue(){ return new Queue(MESSAGE_QUEUE_NAME, true); }

	@Bean
	public TopicExchange messageExchange(){ return new TopicExchange(CHAT_EXCHANGE_NAME); }

	@Bean
	public Binding messageBinding(Queue messageQueue, TopicExchange messageExchange) {
		return BindingBuilder.bind(messageQueue).to(messageExchange).with(MESSAGE_ROUTING_KEY);
	}

	@Bean
	ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory();
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
		return rabbitTemplate;
	}
}
