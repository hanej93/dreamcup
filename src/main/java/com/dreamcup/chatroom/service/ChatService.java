package com.dreamcup.chatroom.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.entity.Chat;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.repository.ChatRepository;
import com.dreamcup.chatroom.repository.ChatRoomRepository;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.member.entity.Participant;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

	private final ChatRepository chatRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ParticipantRepository participantRepository;

	@Transactional
	public Chat save(ChatVo chatVo) {
		Participant sender = chatVo.getSenderId() != null ?  participantRepository.findById(chatVo.getSenderId())
			.orElseThrow(UserNotFoundException::new) : null;

		ChatRoom chatRoom = chatRoomRepository.findById(chatVo.getChatRoomId())
			.orElseThrow();

		Chat chat = Chat.builder()
			.chatRoom(chatRoom)
			.message(chatVo.getMessage())
			.sender(sender)
			.messageType(chatVo.getMessageType())
			.build();

		return chatRepository.save(chat);
	}

}
