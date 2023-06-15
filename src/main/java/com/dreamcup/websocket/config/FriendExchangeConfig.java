package com.dreamcup.websocket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendExchangeConfig {

	public static final String FRIEND_EXCHANGE_NAME = "friend.exchange";
	public static final String FRIEND_STATUS_ROUTING_KEY = "friendStatus";
	public static final String FRIEND_STATUS_QUEUE_NAME = "friendStatus.queue";

	@Bean
	public TopicExchange friendExchange() {
		return new TopicExchange(FRIEND_EXCHANGE_NAME);
	}

	@Bean
	public Queue friendStatusQueue() {
		return new Queue(FRIEND_STATUS_QUEUE_NAME, true);
	}

	@Bean
	public Binding friendStatusBinding(Queue friendStatusQueue, TopicExchange friendExchange) {
		return BindingBuilder.bind(friendStatusQueue).to(friendExchange).with(FRIEND_STATUS_ROUTING_KEY);
	}

}
