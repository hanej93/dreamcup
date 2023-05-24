package com.dreamcup.member.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(length = 50, unique = true)
	private String username;

	@Column(length = 100)
	private String password;

	@Column(length = 50)
	private String nickname;

	@Column(name = "activated")
	private boolean activated;

	@ManyToMany
	@JoinTable(
		name = "user_authority",
		joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
		inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
	)
	private Set<Authority> authorities = new HashSet<>();

	@Builder
	public Member(String username, String password, String nickname, boolean activated) {
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.activated = activated;
	}
}
