package com.dreamcup.member.entity;

import com.dreamcup.member.code.AuthorityEnum;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
public class MemberAuthority {

	@EmbeddedId
	private MemberAuthorityId id;

	public void addMemberAuthority(Member member, AuthorityEnum authorityEnum) {
		this.id = MemberAuthorityId.builder()
			.member(member)
			.authority(authorityEnum)
			.build();

		member.getAuthorities().add(this);
	}
}
