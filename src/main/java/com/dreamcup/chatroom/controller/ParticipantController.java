package com.dreamcup.chatroom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.chatroom.dto.request.PrivateChatRoomJoinRequestDto;
import com.dreamcup.chatroom.dto.request.PublicChatRoomJoinRequestDto;
import com.dreamcup.chatroom.service.ParticipantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ParticipantController {

	private final ParticipantService participantService;

	@PostMapping("/chatRoom/public-join")
	public ResponseEntity joinPublicChatRoom(@RequestBody @Valid PublicChatRoomJoinRequestDto requestDto) {
		participantService.joinPublicChatRoom(requestDto);
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping("/chatRoom/private-join")
	public ResponseEntity joinPrivateChatRoom(@RequestBody @Valid PrivateChatRoomJoinRequestDto requestDto) {
		participantService.joinPrivateChatRoom(requestDto);
		return new ResponseEntity(HttpStatus.OK);
	}

}
