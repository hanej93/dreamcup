package com.dreamcup.member.entity;

import static jakarta.persistence.FetchType.*;

import com.dreamcup.member.code.AuthorityEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@IdClass(MemberAuthorityId.class)
@NoArgsConstructor
public class MemberAuthority {

	@Id
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Id
	@Enumerated(EnumType.STRING)
	private AuthorityEnum authority;

	public MemberAuthority(Member member, AuthorityEnum authority) {
		this.member = member;
		this.authority = authority;
	}

}
