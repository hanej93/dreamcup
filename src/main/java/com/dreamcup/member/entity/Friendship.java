package com.dreamcup.member.entity;

import com.dreamcup.common.entity.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friendship extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "friend_id")
	private Member friend;

	@Column(nullable = false)
	private boolean accepted;

	@Builder
	public Friendship(Member member, Member friend, boolean accepted) {
		this.member = member;
		this.friend = friend;
		this.accepted = accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
