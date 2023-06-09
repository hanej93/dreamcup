package com.dreamcup.config.jwt.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

	private Long memberId;
	private String username;
	private String nickname;
	private String nameTag;
	private Set<AuthorityEnum> authorities;

	public LoginResponseDto(Member member) {
		this.memberId = member.getId();
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.nameTag = member.getNameTag();
		this.authorities = member.getAuthorities().stream()
			.map(auth -> auth.getAuthority())
			.collect(Collectors.toSet());
	}
}
