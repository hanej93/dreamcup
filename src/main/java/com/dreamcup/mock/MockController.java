package com.dreamcup.mock;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.service.ChatRoomService;
import com.dreamcup.chatroom.service.ChatService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Profile("local")
public class MockController {

	private final MockService mockService;

	@GetMapping("/")
	public String index() {
		return "home";
	}


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

	@GetMapping("/api/login")
	public String loginForm() {
		return "login";
	}

	@PostConstruct
	public void init() {
		// 회원 등록
		mockService.generateMembers();

		// 친구 등록(신청)
		mockService.generateFriends();
		
		// 채팅방 생성
		mockService.generateChatRooms();
	}
	
}
