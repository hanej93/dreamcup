package com.dreamcup.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.entity.Chat;
import com.dreamcup.entity.ChatRoom;
import com.dreamcup.repository.ChatRepository;

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
