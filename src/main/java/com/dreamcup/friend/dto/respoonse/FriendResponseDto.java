package com.dreamcup.friend.dto.respoonse;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FriendResponseDto {

	private Long memberId;
	private String nickname;

	@QueryProjection
	public FriendResponseDto(Long memberId, String nickname) {
		this.memberId = memberId;
		this.nickname = nickname;
	}

}
