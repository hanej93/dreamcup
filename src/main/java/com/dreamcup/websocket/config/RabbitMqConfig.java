package com.dreamcup.websocket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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
	public static final String STATUS_ROUTING_KEY = "status.*";
	public static final String STATUS_QUEUE_NAME = "status.queue";
	public static final String JOIN_ROUTING_KEY = "join.*";
	public static final String JOIN_QUEUE_NAME = "join.queue";
	public static final String LEAVE_ROUTING_KEY = "leave.*";
	public static final String LEAVE_QUEUE_NAME = "leave.queue";

	@Bean
	public TopicExchange messageExchange(){ return new TopicExchange(CHAT_EXCHANGE_NAME); }

	@Bean
	public Queue messageQueue(){ return new Queue(MESSAGE_QUEUE_NAME, true); }

	@Bean
	public Binding messageBinding(Queue messageQueue, TopicExchange messageExchange) {
		return BindingBuilder.bind(messageQueue).to(messageExchange).with(MESSAGE_ROUTING_KEY);
	}

	@Bean
	public Queue joinQueue() {
		return new Queue(JOIN_QUEUE_NAME, true);
	}

	@Bean
	public Binding joinBinding(Queue joinQueue, TopicExchange messageExchange) {
		return BindingBuilder.bind(joinQueue).to(messageExchange).with(JOIN_ROUTING_KEY);
	}

	@Bean
	public Queue leaveQueue() {
		return new Queue(LEAVE_QUEUE_NAME, true);
	}

	@Bean
	public Binding leaveBinding(Queue leaveQueue, TopicExchange messageExchange) {
		return BindingBuilder.bind(leaveQueue).to(messageExchange).with(LEAVE_ROUTING_KEY);
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
