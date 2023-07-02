package com.dreamcup.friend.dto.respoonse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendChatReadUpdateResponseDto {

	private Long memberId;
	private Long lastChatId;

}
