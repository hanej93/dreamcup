package com.dreamcup.member.entity;

import java.io.Serializable;

import com.dreamcup.member.code.AuthorityEnum;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberAuthorityId implements Serializable {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	private AuthorityEnum authority;

	@Builder
	public MemberAuthorityId(Member member, AuthorityEnum authority) {
		this.member = member;
		this.authority = authority;
	}
}
