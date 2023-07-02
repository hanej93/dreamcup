package com.dreamcup.member.entity;

import com.dreamcup.common.entity.common.BaseTimeEntity;
import com.dreamcup.member.code.MemberStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatus extends BaseTimeEntity {

	@Id
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private MemberStatusEnum status;

}
