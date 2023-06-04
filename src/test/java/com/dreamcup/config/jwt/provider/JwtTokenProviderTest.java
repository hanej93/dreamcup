package com.dreamcup.config.jwt.provider;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import com.dreamcup.config.auth.LoginUser;
import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberAuthority;

class JwtTokenProviderTest {

	@Test
	@DisplayName("토큰 발급")
	void generateTokenWithPrefix() throws Exception {
	    // given
		Member member = Member.builder()
			.username("user1")
			.build();
		MemberAuthority memberAuthority = new MemberAuthority();
		memberAuthority.addMemberAuthority(member, AuthorityEnum.ROLE_USER);

		LoginUser loginUser = new LoginUser(member);
		// when
		String jwtToken = JwtTokenProvider.generateTokenWithPrefix(loginUser);

		// then
		assertThat(jwtToken).isNotNull();
		assertThat(jwtToken).startsWith(JwtConfigProperties.TOKEN_PREFIX);
	}

	@Test
	@DisplayName("Valid Token")
	void validateToken() {
		// given
		Member member = Member.builder()
			.username("user1")
			.build();
		MemberAuthority memberAuthority = new MemberAuthority();
		memberAuthority.addMemberAuthority(member, AuthorityEnum.ROLE_USER);

		LoginUser loginUser = new LoginUser(member);
		String bearerToken = JwtTokenProvider.generateTokenWithPrefix(loginUser);
		String jwtToken = bearerToken.replace(JwtConfigProperties.TOKEN_PREFIX, "");

		// when
		boolean result = JwtTokenProvider.validateToken(jwtToken);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("인증 테스트")
	void getAuthentication() {
		// given
		Member member = Member.builder()
			.username("user1")
			.build();
		MemberAuthority memberAuthority = new MemberAuthority();
		memberAuthority.addMemberAuthority(member, AuthorityEnum.ROLE_USER);

		LoginUser loginUser = new LoginUser(member);
		String bearerToken = JwtTokenProvider.generateTokenWithPrefix(loginUser);
		String jwtToken = bearerToken.replace(JwtConfigProperties.TOKEN_PREFIX, "");

		// when
		Authentication authentication = JwtTokenProvider.getAuthentication(jwtToken);

		// then
		assertThat(authentication.getPrincipal()).isNotNull();
		assertThat(authentication.getAuthorities()).isNotNull();
		assertThat((LoginUser) authentication.getPrincipal()).isInstanceOf(LoginUser.class);

		LoginUser authLoginUser = (LoginUser) authentication.getPrincipal();
		assertThat(authLoginUser.getUsername()).isEqualTo("user1");
		assertThat(authLoginUser.getAuthorities()).isEqualTo(AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

}