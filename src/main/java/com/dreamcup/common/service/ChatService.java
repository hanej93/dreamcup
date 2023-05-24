package com.dreamcup.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.common.entity.Chat;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.common.repository.ChatRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;

	// todo : delete me (for test)
	@Transactional
	public Long save(String message, ChatRoom chatRoom) {
		Chat chat = Chat.builder()
			.message(message)
			.chatRoom(chatRoom)
			.build();
		return chatRepository.save(chat).getChatId();
	}

}
