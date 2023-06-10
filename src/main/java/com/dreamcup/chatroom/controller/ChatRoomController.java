package com.dreamcup.chatroom.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.request.ChatRoomUpdateRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import com.dreamcup.chatroom.service.ChatRoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@GetMapping("/chatRoom")
	public ResponseEntity<Page<ChatRoomResponseDto>> getList(@ModelAttribute ChatRoomSearchRequestDto requestDto) {
		Page<ChatRoomResponseDto> pagedDtos = chatRoomService.getPagedChatRooms(requestDto);
		return ResponseEntity.ok(pagedDtos);
	}

	@GetMapping("/chatRoom/{chatRoomId}")
	public ResponseEntity<ChatRoomResponseDto> get(@PathVariable Long chatRoomId) {
		ChatRoomResponseDto response = chatRoomService.findById(chatRoomId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/chatRoom")
	public ResponseEntity<Long> createChatRoom(@RequestBody @Valid ChatRoomSaveRequestDto requestDto) {
		Long id = chatRoomService.createChatRoom(requestDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@PatchMapping("/chatRoom/{chatRoomId}")
	public ResponseEntity<Long> update(@PathVariable Long chatRoomId,
		@RequestBody @Valid ChatRoomUpdateRequestDto requestDto) {
		Long id = chatRoomService.update(chatRoomId, requestDto);
		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/chatRoom/{chatRoomId}")
	public ResponseEntity<Long> delete(@PathVariable Long chatRoomId) {
		chatRoomService.delete(chatRoomId);
		return ResponseEntity.ok(chatRoomId);
	}

}
