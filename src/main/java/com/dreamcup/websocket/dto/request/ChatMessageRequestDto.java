package com.dreamcup.websocket.dto.request;

import com.dreamcup.chatroom.code.MessageType;

import lombok.Getter;

@Getter
public class ChatMessageRequestDto {

	private Long chatRoomId;
	private Long senderId;
	private String message;
	private MessageType messageType;

}
