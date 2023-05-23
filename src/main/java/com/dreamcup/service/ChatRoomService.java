package com.dreamcup.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.domain.ChatRoom;
import com.dreamcup.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	// todo : delete me (for test)
	@Transactional
	public ChatRoom createChatRoom(String title) {
		return chatRoomRepository.save(new ChatRoom(title));
	}

}
