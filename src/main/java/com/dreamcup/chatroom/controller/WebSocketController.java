package com.dreamcup.chatroom.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.config.RabbitMqConfig;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {

	private final RabbitTemplate rabbitTemplate;

	@MessageMapping("/message")
	public void sendMessage(ChatMessage chatMessage) {
		log.debug("WebSocketController.sendMessage : {}", chatMessage);
		rabbitTemplate.convertAndSend(RabbitMqConfig.CHAT_EXCHANGE_NAME, "message." + chatMessage.getChatRoomUid(), chatMessage);
	}

	@Data
	public static class ChatMessage {
		private String chatRoomUid;
		private String sender;
		private String content;
	}

}
