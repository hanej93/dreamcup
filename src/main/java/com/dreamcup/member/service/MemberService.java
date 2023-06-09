package com.dreamcup.member.service;

import java.util.Arrays;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberAuthority;
import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.exception.DuplicateMemberException;
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
	public Long signup(MemberSignupRequestDto requestDto) {
		memberRepository.findWithAuthoritiesByUsername(requestDto.getUsername())
			.ifPresent(user -> {
				throw new DuplicateMemberException();
			});

		Member member = Member.builder()
			.username(requestDto.getUsername())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickName(requestDto.getNickname())
			.build();

		MemberAuthority memberAuthority = new MemberAuthority();
		memberAuthority.addMemberAuthority(member, AuthorityEnum.ROLE_USER);

		memberRepository.save(member);

		return member.getId();
	}

}
