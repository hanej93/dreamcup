package com.dreamcup.chatroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.chatroom.dto.request.ChatRoomJoinRequestDto;
import com.dreamcup.chatroom.service.ParticipantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParticipantController {

	private final ParticipantService participantService;

	@PostMapping("/chatRoom/join")
	public ResponseEntity joinChatRoom(@RequestBody @Valid ChatRoomJoinRequestDto requestDto) {
		participantService.joinChatRoom(requestDto);
		return new ResponseEntity(HttpStatus.OK);
	}

}
