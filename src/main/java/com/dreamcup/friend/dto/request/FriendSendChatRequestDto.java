package com.dreamcup.friend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendSendChatRequestDto {

	private String message;
	private Long senderId;
	private Long receiverId;

}
