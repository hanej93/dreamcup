package com.dreamcup.member.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.dreamcup.member.code.AuthorityEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Participant {

	@Column(length = 50, unique = true)
	private String username;

	@Column(length = 100)
	private String password;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<MemberAuthority> authorities = new HashSet<>();

	@Override
	public boolean isAnonymous() {
		return false;
	}

	@Builder
	public Member(String nickName, String nameTag, String username, String password) {
		super(nickName, nameTag);
		this.username = username;
		this.password = password;
	}

	public void addMemberAuthorities(MemberAuthority... authorities) {
		addMemberAuthorities(Arrays.asList(authorities));
	}

	public void addMemberAuthorities(Collection<MemberAuthority> authorities) {
		this.authorities.addAll(authorities);
	}

}
