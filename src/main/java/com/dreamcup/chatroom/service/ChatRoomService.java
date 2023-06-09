package com.dreamcup.chatroom.service;

import static org.springframework.util.StringUtils.*;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.code.MessageType;
import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomUpdateRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.entity.ChatRoomParticipants;
import com.dreamcup.chatroom.exception.ChatRoomNotFoundException;
import com.dreamcup.chatroom.repository.ChatRoomRepository;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.member.entity.Participant;
import com.dreamcup.member.exception.MemberNotFoundException;
import com.dreamcup.member.repository.MemberRepository;
import com.dreamcup.member.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

	private final PasswordEncoder passwordEncoder;
	private final ChatService chatService;
	private final ChatRoomRepository chatRoomRepository;
	private final ParticipantRepository participantRepository;

	@Transactional
	public Long createChatRoom(ChatRoomSaveRequestDto requestDto) {
		/**
		 * todo: 채팅방 생성 로직
		 * 1. 채팅방 생성
		 * 2. 채팅방 참여
		 * -> 채팅방 입장 후 시스템 생성 메시지?
		 * 3. 시스템 채팅 메시지 발송
		 */

		ChatRoom chatRoom = convertToChatRoomEntity(requestDto);

		chatRoomRepository.save(chatRoom);

		ChatVo chatVo = ChatVo.builder()
			.chatRoomId(chatRoom.getChatRoomId())
			.message("채팅방이 생성되었습니다.") // todo: change constraint
			.messageType(MessageType.SYSTEM)
			.senderId(null)
			.build();
		// chatService.sendMessage(chatVo);
		chatService.save(chatVo);

		return chatRoom.getChatRoomId();
	}

	private ChatRoom convertToChatRoomEntity(ChatRoomSaveRequestDto requestDto) {
		ChatRoom.ChatRoomBuilder chatRoomBuilder = ChatRoom.builder()
			.title(requestDto.getTitle())
			.maxUserCount(requestDto.getUserMaxCount());

		if (hasText(requestDto.getRawPassword())) {
			chatRoomBuilder.password(passwordEncoder.encode(requestDto.getRawPassword()));
		}

		Participant participant = participantRepository.findById(requestDto.getCreator())
			.orElseThrow(MemberNotFoundException::new);

		chatRoomBuilder.creator(participant);

		ChatRoom chatRoom = chatRoomBuilder.build();

		chatRoom.addParticipant(participant);

		return chatRoom;
	}

	@Transactional
	public Long update(Long id, ChatRoomUpdateRequestDto requestDto) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow(ChatRoomNotFoundException::new);

		chatRoom.update(requestDto.getTitle());

		return id;
	}

	public List<ChatRoomResponseDto> getPagenatedList(ChatRoomSearchRequestDto requestDto) {
		return chatRoomRepository.getPagenatedList(requestDto);
	}

	public ChatRoomResponseDto findById(Long id) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow(ChatRoomNotFoundException::new);
		return new ChatRoomResponseDto(chatRoom);
	}

	@Transactional
	public void delete(Long id) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow();
		chatRoomRepository.delete(chatRoom);
	}

	// todo : delete me (for test)
	@Transactional
	public ChatRoom createChatRoom(String title) {
		return chatRoomRepository.save(new ChatRoom(title));
	}

}
