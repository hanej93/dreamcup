package com.dreamcup.chatroom.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.chatroom.service.ChatService;
import com.dreamcup.chatroom.vo.ChatVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {

	private final ChatService chatService;

	@MessageMapping("/message")
	public void sendMessage(ChatVo chatVo) {
		log.debug("WebSocketController.sendMessage : {}", chatVo);
		chatService.sendChatMessage(chatVo);
	}

}
