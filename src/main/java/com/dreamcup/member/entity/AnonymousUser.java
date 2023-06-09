package com.dreamcup.member.entity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnonymousUser extends Participant {

	@Override
	public boolean isAnonymous() {
		return true;
	}

	@Builder
	public AnonymousUser(String nickName, String nameTag) {
		super(nickName, nameTag);
	}
}
