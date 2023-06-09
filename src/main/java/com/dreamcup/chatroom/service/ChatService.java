package com.dreamcup.chatroom.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.entity.Chat;
import com.dreamcup.chatroom.repository.ChatRepository;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.config.RabbitMqConfig;
import com.dreamcup.member.entity.Participant;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final RabbitTemplate rabbitTemplate;
	private final ChatRepository chatRepository;
	private final ParticipantRepository participantRepository;

	@Transactional
	public void sendChatMessage(ChatVo chatVo) {
		save(chatVo);
		rabbitTemplate.convertAndSend(RabbitMqConfig.CHAT_EXCHANGE_NAME, "message." + chatVo.getChatRoomId(), chatVo);
	}

	@Transactional
	public Chat save(ChatVo chatVo) {
		Participant sender = participantRepository.findById(chatVo.getSenderId())
			.orElseThrow(UserNotFoundException::new);

		Chat chat = Chat.builder()
			.message(chatVo.getMessage())
			.sender(sender)
			.messageType(chatVo.getMessageType())
			.build();

		return chatRepository.save(chat);
	}

}
