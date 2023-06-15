package com.dreamcup.common.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dreamcup.config.auth.LoginUser;
import com.dreamcup.member.entity.Member;

public class AuthenticationUtil {

	public static Optional<Member> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Optional.empty();
		}
		if (!(authentication.getPrincipal() instanceof LoginUser)) {
			return Optional.empty();
		}
		Member member = ((LoginUser)authentication.getPrincipal()).getMember();
		return Optional.of(member);
	}

}
