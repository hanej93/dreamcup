package com.dreamcup.chatroom.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class ParticipantsInChatRoomResponseDto {

	private Long participantId;
	private String nickname;

	@QueryProjection
	public ParticipantsInChatRoomResponseDto(Long participantId, String nickname) {
		this.participantId = participantId;
		this.nickname = nickname;
	}

}
