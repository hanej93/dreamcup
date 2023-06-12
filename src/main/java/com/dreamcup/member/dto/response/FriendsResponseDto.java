package com.dreamcup.member.dto.response;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FriendsResponseDto {

	private Long memberId;
	private String nickname;

	@QueryProjection
	public FriendsResponseDto(Long memberId, String nickname) {
		this.memberId = memberId;
		this.nickname = nickname;
	}

}
