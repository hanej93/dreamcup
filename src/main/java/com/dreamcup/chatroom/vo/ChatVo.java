package com.dreamcup.chatroom.vo;

import com.dreamcup.chatroom.code.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatVo {

	private Long chatId;
	private Long chatRoomId;
	private Long senderId;
	private String message;
	private MessageType messageType;

}
