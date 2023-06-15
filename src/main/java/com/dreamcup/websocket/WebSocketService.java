package com.dreamcup.websocket;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.entity.Chat;
import com.dreamcup.chatroom.service.ChatService;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.websocket.config.RabbitMqConfig;
import com.dreamcup.websocket.dto.request.ChatMessageRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WebSocketService {

	private final RabbitTemplate rabbitTemplate;
	private final ChatService chatService;

	@Transactional
	public void sendChatMessage(ChatMessageRequestDto requestDto) {
		ChatVo chatVo = ChatVo.builder()
			.message(requestDto.getMessage())
			.chatRoomId(requestDto.getChatRoomId())
			.senderId(requestDto.getSenderId())
			.messageType(requestDto.getMessageType())
			.build();

		Chat chat = chatService.save(chatVo);
		chatVo.setChatId(chat.getId());

		rabbitTemplate.convertAndSend(RabbitMqConfig.CHAT_EXCHANGE_NAME, "message." + chatVo.getChatRoomId(), chatVo);
	}

}
