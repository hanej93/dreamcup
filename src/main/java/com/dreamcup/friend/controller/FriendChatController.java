package com.dreamcup.friend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.friend.dto.request.FriendChatsRequestDto;
import com.dreamcup.friend.dto.request.FriendSendChatRequestDto;
import com.dreamcup.friend.dto.respoonse.FriendChatResponseDto;
import com.dreamcup.friend.service.FriendChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendChatController {

	private final FriendChatService friendChatService;

	
	// todo : @MessageMapping 으로 추후에 변경
	// 친구에게 메시지 전송
	@PostMapping("/friend-chats")
	public void sendChatMessage(@RequestBody @Valid FriendSendChatRequestDto requestDto) {
		friendChatService.sendChatMessage(requestDto);
	}

	// 친구 대화 목록 조회
	@GetMapping("/friend-chats")
	public ResponseEntity<List<FriendChatResponseDto>> getChatMessages(@ModelAttribute @Valid FriendChatsRequestDto requestDto) {
		List<FriendChatResponseDto> result = friendChatService.getChatMessages(requestDto);
		return ResponseEntity.ok(result);
	}

}
