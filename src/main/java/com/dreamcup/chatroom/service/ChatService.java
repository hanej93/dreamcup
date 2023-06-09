package com.dreamcup.chatroom.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.entity.Chat;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.repository.ChatRepository;
import com.dreamcup.chatroom.repository.ChatRoomRepository;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.config.RabbitMqConfig;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.exception.MemberNotFoundException;
import com.dreamcup.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final RabbitTemplate rabbitTemplate;
	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void sendMessage(ChatVo chatVo) {
		save(chatVo);
		rabbitTemplate.convertAndSend(RabbitMqConfig.CHAT_EXCHANGE_NAME, "message." + chatVo.getChatRoomId(), chatVo);
	}

	@Transactional
	public void save(ChatVo chatVo) {
		ChatRoom chatRoom = chatRoomRepository.findById(chatVo.getChatRoomId())
			.orElseThrow(() -> new IllegalArgumentException()); // todo : ChatRoomNotFoundException

		Member sender = null;
		if (chatVo.getSenderId() != null) {
			sender = memberRepository.findById(chatVo.getSenderId())
				.orElseThrow(MemberNotFoundException::new);
		}

		Chat chat = Chat.builder()
			.message(chatVo.getMessage())
			// .sender(sender)
			.chatRoom(chatRoom)
			.messageType(chatVo.getMessageType())
			.build();

		Long chatId = chatRepository.save(chat).getChatId();
		chatVo.setChatId(chatId);
	}

}
