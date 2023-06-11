package com.dreamcup.chatroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomLeaveRequestDto {

	private Long ChatRoomId;
	private Long participantId;

}
