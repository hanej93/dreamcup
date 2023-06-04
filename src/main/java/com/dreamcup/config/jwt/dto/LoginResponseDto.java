package com.dreamcup.config.jwt.dto;

import com.dreamcup.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

	private Long id;
	private String username;

	public LoginResponseDto(Member member) {
		this.id = member.getMemberId();
		this.username = member.getUsername();
	}

}
