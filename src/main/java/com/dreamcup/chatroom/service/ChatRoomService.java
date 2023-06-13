package com.dreamcup.chatroom.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.code.MessageType;
import com.dreamcup.chatroom.dto.request.ChatRoomLeaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.request.ParticipantsInChatRoomRequestDto;
import com.dreamcup.chatroom.dto.request.PrivateChatRoomJoinRequestDto;
import com.dreamcup.chatroom.dto.request.PublicChatRoomJoinRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import com.dreamcup.chatroom.dto.response.ParticipantsInChatRoomResponseDto;
import com.dreamcup.chatroom.entity.Chat;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.exception.AlreadyParticipantException;
import com.dreamcup.chatroom.exception.ChatRoomNotFoundException;
import com.dreamcup.chatroom.exception.MaxUserLimitExceededException;
import com.dreamcup.chatroom.repository.ChatRoomParticipantsRepository;
import com.dreamcup.chatroom.repository.ChatRoomRepository;
import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.common.util.CommonUtil;
import com.dreamcup.member.entity.Participant;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatService chatService;
	private final ChatRoomRepository chatRoomRepository;
	private final ParticipantRepository participantRepository;
	private final ChatRoomParticipantsRepository chatRoomParticipantsRepository;

	public Page<ChatRoomResponseDto> getPagedChatRooms(ChatRoomSearchRequestDto requestDto) {
		return chatRoomRepository.getPagedChatRooms(requestDto);
	}

	public ChatRoomResponseDto findChatRoomById(Long id) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
			.orElseThrow(ChatRoomNotFoundException::new);
		return new ChatRoomResponseDto(chatRoom);
	}

	// 채팅방 생성
	@Transactional
	public Long createChatRoom(ChatRoomSaveRequestDto requestDto) {
		Participant creator = participantRepository.findById(requestDto.getCreatorId())
			.orElseThrow(UserNotFoundException::new);

		ChatRoom chatRoom = ChatRoom.builder()
			.title(requestDto.getTitle())
			.creator(creator)
			.maxUserCount(requestDto.getUserMaxCount())
			.privateCode(generateUniquePrivateCode())
			.isPrivate(requestDto.isPrivateRoom())
			.build();

		chatRoom.addParticipant(creator);

		ChatVo chatVo = ChatVo.builder()
			.chatRoomId(chatRoom.getId())
			.message("채팅방이 생성되었습니다.")
			.messageType(MessageType.SYSTEM)
			.build();

		Chat chat = chatService.save(chatVo);
		chatRoom.addChat(chat);

		chatRoomRepository.save(chatRoom);
		return chatRoom.getId();
	}

	private String generateUniquePrivateCode() {
		String privateCode = CommonUtil.randomAlphaNumeric(6);
		boolean isDuplicate = chatRoomRepository.existsByPrivateCode(privateCode);

		if (isDuplicate) {
			return generateUniquePrivateCode();
		}

		return privateCode;
	}

	@Transactional
	public Long joinPublicChatRoom(PublicChatRoomJoinRequestDto requestDto) {
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

		return chatRoom.getId();
	}

	@Transactional
	public Long joinPrivateChatRoom(PrivateChatRoomJoinRequestDto requestDto) {
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

		return chatRoom.getId();
	}

	@Transactional
	public void leaveChatRoom(ChatRoomLeaveRequestDto requestDto) {
		ChatRoom chatRoom = chatRoomRepository.findById(requestDto.getChatRoomId())
			.orElseThrow(ChatRoomNotFoundException::new);
		Participant participant = participantRepository.findById(requestDto.getParticipantId())
			.orElseThrow(UserNotFoundException::new);

		chatRoom.removeParticipant(participant);
	}

	public List<ParticipantsInChatRoomResponseDto> findMemberInChatRoom(ParticipantsInChatRoomRequestDto requestDto) {
		return chatRoomParticipantsRepository.getParticipantsByChatRoomId(requestDto.getChatRoomId());
	}
}
