package com.dreamcup.friend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendChatReadUpdateRequestDto {

	private Long memberId;
	private Long friendId;
	private Long lastChatId;

}
