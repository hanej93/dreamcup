package com.dreamcup.member.dto.response;

import com.dreamcup.member.code.MemberStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatusDto {

	private Long memberId;
	private MemberStatusEnum status;

}
