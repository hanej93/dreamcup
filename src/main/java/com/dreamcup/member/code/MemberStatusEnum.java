package com.dreamcup.member.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberStatusEnum {

	online("온라인"), offline("오프라인");

	private final String value;

}
