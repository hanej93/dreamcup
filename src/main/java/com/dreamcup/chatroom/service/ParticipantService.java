package com.dreamcup.chatroom.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.dto.request.ChatRoomJoinRequestDto;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.exception.ChatRoomNotFoundException;
import com.dreamcup.chatroom.repository.ChatRoomParticipantsRepository;
import com.dreamcup.chatroom.repository.ChatRoomRepository;
import com.dreamcup.member.entity.Participant;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipantService {

	private final ParticipantRepository participantRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomParticipantsRepository chatRoomParticipantsRepository;

	@Transactional
	public void joinChatRoom(ChatRoomJoinRequestDto requestDto) {
		boolean isAlreadyParticipant = chatRoomParticipantsRepository.existsByChatRoomIdAndParticipantId(requestDto.getChatRoomId(),
			requestDto.getParticipantId());

		if (isAlreadyParticipant) {
			throw new IllegalArgumentException();
		}

		ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getChatRoomId())
			.orElseThrow(ChatRoomNotFoundException::new);

		if (chatRoom.isOverMaxUser()) {
			throw new IllegalArgumentException();
		}

		Participant participant = participantRepository.findById(requestDto.getParticipantId())
			.orElseThrow(UserNotFoundException::new);

		chatRoom.addParticipant(participant);
	}

}
