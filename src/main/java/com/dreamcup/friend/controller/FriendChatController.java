package com.dreamcup.friend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.friend.dto.request.FriendChatRequestDto;
import com.dreamcup.friend.dto.request.FriendSendChatRequestDto;
import com.dreamcup.friend.service.FriendChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendChatController {

	private final FriendChatService friendChatService;

	
	// 메시지 보내는 로직
	@PostMapping("/chats")
	public void sendChatMessage(@RequestBody @Valid FriendSendChatRequestDto requestDto) {
		friendChatService.sendChatMessage(requestDto);
	}


	// 메시지 조회하는 로직
	@GetMapping("/chats")
	public void getChatMessages(@ModelAttribute @Valid FriendChatRequestDto requestDto) {

	}

}
