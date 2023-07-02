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

	public static final String FRIEND_MESSAGE_READ_ROUTING_KEY = "message.read.*";
	public static final String FRIEND_MESSAGE_READ_QUEUE_NAME = "message.read.queue";

	public static final String FRIEND_STATUS_ROUTING_KEY = "status.*";
	public static final String FRIEND_STATUS_QUEUE_NAME = "status.queue";

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

	@Bean
	public Queue friendMessageReadQueue() {
		return new Queue(FRIEND_MESSAGE_READ_QUEUE_NAME, true);
	}

	@Bean
	public Binding friendMessageReadBinding(Queue friendMessageReadQueue, TopicExchange friendExchange) {
		return BindingBuilder.bind(friendMessageReadQueue).to(friendExchange).with(FRIEND_MESSAGE_READ_ROUTING_KEY);
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
