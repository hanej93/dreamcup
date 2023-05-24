package com.dreamcup.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.entity.Authority;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void signup(MemberSignupRequestDto requestDto) {
		memberRepository.findOneWithAuthoritiesByUsername(requestDto.getUsername())
			.ifPresent(user -> {
				throw new IllegalArgumentException("이미 가입되어 있는 유저입니다.");
			});

		Member member = Member.builder()
			.username(requestDto.getUsername())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.activated(true)
			.build();

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();
		member.getAuthorities().add(authority);

		memberRepository.save(member);
	}


}
