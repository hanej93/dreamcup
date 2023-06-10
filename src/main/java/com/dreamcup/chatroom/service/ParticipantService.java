package com.dreamcup.chatroom.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.dto.request.PrivateChatRoomJoinRequestDto;
import com.dreamcup.chatroom.dto.request.PublicChatRoomJoinRequestDto;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.exception.AlreadyParticipantException;
import com.dreamcup.chatroom.exception.ChatRoomNotFoundException;
import com.dreamcup.chatroom.exception.MaxUserLimitExceededException;
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
	public void joinPublicChatRoom(PublicChatRoomJoinRequestDto requestDto) {
		boolean isAlreadyParticipant = chatRoomParticipantsRepository.existsByChatRoomIdAndParticipantId(requestDto.getChatRoomId(),
			requestDto.getParticipantId());

		if (isAlreadyParticipant) {
			throw new AlreadyParticipantException();
		}

		ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getChatRoomId())
			.orElseThrow(ChatRoomNotFoundException::new);

		if (chatRoom.isOverMaxUser()) {
			throw new MaxUserLimitExceededException();
		}

		Participant participant = participantRepository.findById(requestDto.getParticipantId())
			.orElseThrow(UserNotFoundException::new);

		chatRoom.addParticipant(participant);
	}

	@Transactional
	public void joinPrivateChatRoom(PrivateChatRoomJoinRequestDto requestDto) {
		boolean isAlreadyParticipant = chatRoomParticipantsRepository.existsByPrivateCodeAndParticipantId(
			requestDto.getPrivateCode(), requestDto.getParticipantId());

		if (isAlreadyParticipant) {
			throw new AlreadyParticipantException();
		}

		ChatRoom chatRoom = chatRoomRepository.findByPrivateCode(requestDto.getPrivateCode())
			.orElseThrow(ChatRoomNotFoundException::new);

		if (chatRoom.isOverMaxUser()) {
			throw new MaxUserLimitExceededException();
		}

		Participant participant = participantRepository.findById(requestDto.getParticipantId())
			.orElseThrow(UserNotFoundException::new);

		chatRoom.addParticipant(participant);
	}

}
