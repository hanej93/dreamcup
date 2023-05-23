package com.dreamcup.service;

import com.dreamcup.domain.ChatRoom;
import com.dreamcup.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.dto.request.ChatRoomUpdateRequestDto;
import com.dreamcup.dto.response.ChatRoomResponseDto;
import com.dreamcup.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	public Long save(ChatRoomSaveRequestDto requestDto) {
		return chatRoomRepository.save(requestDto.toEntity()).getId();
	}

	@Transactional
	public Long update(Long id, ChatRoomUpdateRequestDto requestDto) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. id=" + id));

		chatRoom.update(requestDto.getTitle());

		return id;
	}

	public List<ChatRoomResponseDto> getPagenatedList(ChatRoomSearchRequestDto requestDto) {
		return chatRoomRepository.getPagenatedList(requestDto);
	}

	public ChatRoomResponseDto findById(Long id) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. id=" + id));
		return new ChatRoomResponseDto(chatRoom);
	}

	@Transactional
	public void delete(Long id) {
		ChatRoom chatRoom = chatRoomRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다. id=" + id));
		chatRoomRepository.delete(chatRoom);
	}

	// todo : delete me (for test)
	@Transactional
	public ChatRoom createChatRoom(String title) {
		return chatRoomRepository.save(new ChatRoom(title));
	}

}
