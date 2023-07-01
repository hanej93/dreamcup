package com.dreamcup.friend.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendExchangeConfig {

	public static final String FRIEND_EXCHANGE_NAME = "friend.exchange";
	public static final String FRIEND_MESSAGE_ROUTING_KEY = "message.*";
	public static final String FRIEND_MESSAGE_QUEUE_NAME = "message.queue";

	@Bean
	public TopicExchange friendExchange() {
		return new TopicExchange(FRIEND_EXCHANGE_NAME);
	}

	@Bean
	public Queue friendMessageQueue() {
		return new Queue(FRIEND_MESSAGE_QUEUE_NAME, true);
	}

	@Bean
	public Binding friendMessageBinding(Queue friendMessageQueue, TopicExchange friendExchange) {
		return BindingBuilder.bind(friendMessageQueue).to(friendExchange).with(FRIEND_MESSAGE_ROUTING_KEY);
	}

}