package com.dreamcup.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamcup.domain.ChatRoom;
import com.dreamcup.service.ChatRoomService;
import com.dreamcup.service.ChatService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final ChatRoomService chatRoomService;
	private final ChatService chatService;

	@GetMapping("/user")
	@ResponseBody
	public String user() {
		return "user";
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	@GetMapping("/auth/login")
	public String loginForm() {
		return "login";
	}

	@PostConstruct
	public void init() {
		// 채팅방 생성
		for (int i = 1; i <= 100; i++) {
			ChatRoom chatRoom = chatRoomService.createChatRoom("제목" + i);
			chatService.save("내용" + i, chatRoom);
			chatService.save("내요옹" + i, chatRoom);
		}
	}
	
}
