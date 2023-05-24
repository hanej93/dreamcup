package com.dreamcup.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.repository.MemberRepository;

@SpringBootTest
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("회원가입")
	void signup() throws Exception {
	    // given
		MemberSignupRequestDto requestDto = MemberSignupRequestDto.builder()
			.username("user")
			.password("1234")
			.nickname("user-nick")
			.build();

		// when
		memberService.signup(requestDto);

	    // then
		Member findMember = memberRepository.findOneWithAuthoritiesByUsername("user")
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		assertThat(findMember).extracting("username").isEqualTo("user");
		assertThat(passwordEncoder.matches("1234", findMember.getPassword())).isTrue();
		assertThat(findMember).extracting("nickname").isEqualTo("user-nick");
		assertThat(findMember.getAuthorities()).extracting("authorityName").containsExactly("ROLE_USER");
	}
}