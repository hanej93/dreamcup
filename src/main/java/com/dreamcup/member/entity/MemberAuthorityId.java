package com.dreamcup.member.entity;

import java.io.Serializable;

import com.dreamcup.member.code.AuthorityEnum;

import lombok.Data;


@Data
public class MemberAuthorityId implements Serializable {

	private Member member;
	private AuthorityEnum authority;

}
