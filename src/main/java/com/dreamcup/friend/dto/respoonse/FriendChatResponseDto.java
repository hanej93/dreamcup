package com.dreamcup.friend.dto.respoonse;

import com.dreamcup.chatroom.code.MessageType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendChatResponseDto {

	private Long id;
	private String message;
	private Long senderId;
	private String senderNickname;
	private String senderNameTag;
	private Long receiverId;
	private String receiverNickname;
	private String receiverNameTag;
	private MessageType messageType;

}
