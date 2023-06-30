package com.dreamcup.member.entity;

import static jakarta.persistence.CascadeType.*;

import java.util.HashSet;
import java.util.Set;

import com.dreamcup.friend.entity.Friendship;
import com.dreamcup.member.code.AuthorityEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Participant {

	@Column(length = 50, unique = true)
	private String username;

	@Column(length = 100)
	private String password;

	@OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
	private Set<MemberAuthority> authorities = new HashSet<>();

	@Override
	public boolean isAnonymous() {
		return false;
	}

	@OneToMany(mappedBy = "friend", cascade = ALL, orphanRemoval = true)
	private Set<Friendship> friends = new HashSet<>();

	@Builder
	public Member(String nickname, String nameTag, String username, String password) {
		super(nickname, nameTag);
		this.username = username;
		this.password = password;
	}

	public void addMemberAuthority(AuthorityEnum authorityEnum) {
		MemberAuthority memberAuthority = new MemberAuthority(this, authorityEnum);
		authorities.add(memberAuthority);
	}

	public void removeMemberAuthority(AuthorityEnum authorityEnum) {
		MemberAuthority memberAuthority = new MemberAuthority(this, authorityEnum);
		authorities.remove(memberAuthority);
	}

}
