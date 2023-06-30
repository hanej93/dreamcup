package com.dreamcup.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.dto.request.ValidNicknameRequestDto;
import com.dreamcup.member.dto.request.ValidUsernameRequestDto;
import com.dreamcup.member.dto.response.MemberResponseDto;
import com.dreamcup.member.dto.response.ValidNicknameResponseDto;
import com.dreamcup.member.dto.response.ValidUsernameResponseDto;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.exception.DuplicateMemberException;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.friend.repository.FriendshipRepository;
import com.dreamcup.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository memberRepository;
	private final FriendshipRepository friendShipRepository;

	// 회원가입
	@Transactional
	public Long signup(MemberSignupRequestDto requestDto) {
		memberRepository.findWithAuthoritiesByUsername(requestDto.getUsername())
			.ifPresent(user -> {
				throw new DuplicateMemberException();
			});

		Member member = Member.builder()
			.username(requestDto.getUsername())
			.password(passwordEncoder.encode(requestDto.getPassword()))
			.nickname(requestDto.getNickname())
			.nameTag(requestDto.getNameTag())
			.build();
		member.addMemberAuthority(AuthorityEnum.ROLE_USER);

		memberRepository.save(member);

		return member.getId();
	}

	// 유저 이름(이메일) 중복 확인
	public ValidUsernameResponseDto validateUsername(ValidUsernameRequestDto requestDto) {
		boolean isDuplicated = memberRepository.existsByUsername(requestDto.getUsername());

		return ValidUsernameResponseDto.builder()
			.duplicated(isDuplicated)
			.build();
	}

	// 닉네임 태그 중복 확인
	public ValidNicknameResponseDto validateNicknameAndNameTag(ValidNicknameRequestDto requestDto) {
		boolean isDuplicated = memberRepository.existsByNicknameAndNameTag(requestDto.getNickname(),
			requestDto.getTagName());

		return ValidNicknameResponseDto.builder()
			.duplicated(isDuplicated)
			.build();
	}

	// 회원 정보 조회
	public MemberResponseDto findMember(Long memberId) {
		Member member = memberRepository.findWithAuthoritiesById(memberId)
			.orElseThrow(UserNotFoundException::new);
		return new MemberResponseDto(member);
	}

}
