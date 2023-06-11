package com.dreamcup.chatroom.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.chatroom.dto.request.ChatRoomLeaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.request.PrivateChatRoomJoinRequestDto;
import com.dreamcup.chatroom.dto.request.PublicChatRoomJoinRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import com.dreamcup.chatroom.service.ChatRoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	// 채팅방 목록 조회
	@GetMapping("/")
	public ResponseEntity<Page<ChatRoomResponseDto>> getList(@RequestBody @Valid ChatRoomSearchRequestDto requestDto) {
		Page<ChatRoomResponseDto> pagedDtos = chatRoomService.getPagedChatRooms(requestDto);
		return ResponseEntity.ok(pagedDtos);
	}

	@GetMapping("/{chatRoomId}")
	public ResponseEntity<ChatRoomResponseDto> get(@PathVariable Long chatRoomId) {
		ChatRoomResponseDto response = chatRoomService.findChatRoomById(chatRoomId);
		return ResponseEntity.ok(response);
	}

	// 채팅방 생성
	@PostMapping("/")
	public ResponseEntity<Long> createChatRoom(@RequestBody @Valid ChatRoomSaveRequestDto requestDto) {
		Long id = chatRoomService.createChatRoom(requestDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	// 채팅방 입장(chatRoomId, participantId)
	@PostMapping("/public-join")
	public ResponseEntity<Long> joinPublicChatRoom(@RequestBody @Valid PublicChatRoomJoinRequestDto requestDto) {
		Long id = chatRoomService.joinPublicChatRoom(requestDto);
		return new ResponseEntity(id, HttpStatus.OK);
	}

	// 채팅방 입장(privateCode, participantId)
	@PostMapping("/private-join")
	public ResponseEntity<Long> joinPrivateChatRoom(@RequestBody @Valid PrivateChatRoomJoinRequestDto requestDto) {
		Long id = chatRoomService.joinPrivateChatRoom(requestDto);
		return new ResponseEntity(id, HttpStatus.OK);
	}

	// 채팅방 나가기
	@PostMapping("/leave")
	public ResponseEntity leaveChatRoom(@RequestBody @Valid ChatRoomLeaveRequestDto requestDto) {
		chatRoomService.leaveChatRoom(requestDto);
		return new ResponseEntity(HttpStatus.OK);
	}

}
