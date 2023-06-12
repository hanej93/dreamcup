package com.dreamcup.member.dto.response;

import java.util.Set;
import java.util.stream.Collectors;

import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

	private Long memberId;
	private String username;
	private String nickname;
	private String nameTag;
	private Set<AuthorityEnum> authorities;

	public MemberResponseDto(Member member) {
		this.memberId = member.getId();
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.nameTag = member.getNameTag();
		this.authorities = member.getAuthorities().stream()
			.map(auth -> auth.getAuthority()).collect(Collectors.toSet());
	}
}
