package com.dreamcup.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.dto.request.ChatRoomUpdateRequestDto;
import com.dreamcup.dto.response.ChatRoomResponseDto;
import com.dreamcup.service.ChatRoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

	private final ChatRoomService chatRoomService;

	@PostMapping("/chatRoom")
	public ResponseEntity<Long> post(@RequestBody @Valid ChatRoomSaveRequestDto requestDto) {
		Long id = chatRoomService.save(requestDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@GetMapping("/chatRoom/{chatRoomId}")
	public ResponseEntity<ChatRoomResponseDto> get(@PathVariable Long chatRoomId) {
		ChatRoomResponseDto response = chatRoomService.findById(chatRoomId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/chatRoom")
	public ResponseEntity<List<ChatRoomResponseDto>> getList(@ModelAttribute ChatRoomSearchRequestDto requestDto) {
		List<ChatRoomResponseDto> pagenatedList = chatRoomService.getPagenatedList(requestDto);
		return ResponseEntity.ok(pagenatedList);
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