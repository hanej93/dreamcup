package com.dreamcup.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dreamcup.chatroom.dto.request.ChatRoomSaveRequestDto;
import com.dreamcup.chatroom.service.ChatRoomService;
import com.dreamcup.chatroom.service.ChatService;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.service.MemberService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MockController {

	private final ChatRoomService chatRoomService;
	private final ChatService chatService;
	private final MemberService memberService;

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
		// 멤버 생성
		for (int i = 1; i <= 10; i++) {
			MemberSignupRequestDto requestDto = MemberSignupRequestDto.builder()
				.username("user" + i)
				.password("1234")
				.nickname("user" + i)
				.build();
			memberService.signup(requestDto);
		}

		// 채팅방 생성
		for (int i = 1; i <= 100; i++) {
			ChatRoomSaveRequestDto chatRoomSaveRequestDto = ChatRoomSaveRequestDto.builder()
				.creatorId(1L)
				.privateRoom(true)
				.title("제목" + i)
				.userMaxCount((i % 8) + 1)
				.build();
			chatRoomService.createChatRoom(chatRoomSaveRequestDto);
		}
	}
	
}
