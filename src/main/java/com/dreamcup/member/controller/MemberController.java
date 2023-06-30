package com.dreamcup.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.dto.request.ValidNicknameRequestDto;
import com.dreamcup.member.dto.request.ValidUsernameRequestDto;
import com.dreamcup.member.dto.response.MemberResponseDto;
import com.dreamcup.member.dto.response.ValidNicknameResponseDto;
import com.dreamcup.member.dto.response.ValidUsernameResponseDto;
import com.dreamcup.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 회원 등록
	@PostMapping("/signup")
	public ResponseEntity<Long> signup(@RequestBody @Valid MemberSignupRequestDto requestDto) {
		Long id = memberService.signup(requestDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
	
	// 유저 이름(이메일) 중복 확인
	@PostMapping("/valid-username")
	public ResponseEntity<ValidUsernameResponseDto> validateUsername(@RequestBody @Valid ValidUsernameRequestDto requestDto) {
		ValidUsernameResponseDto result = memberService.validateUsername(requestDto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 닉네임 중복 확인
	@PostMapping("/valid-nickname")
	public ResponseEntity<ValidNicknameResponseDto> validateNickname(@RequestBody @Valid ValidNicknameRequestDto requestDto) {
		ValidNicknameResponseDto result = memberService.validateNicknameAndNameTag(requestDto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 회원 정보 조회
	@GetMapping("/members/{memberId}")
	public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {
		MemberResponseDto member = memberService.findMember(memberId);
		return new ResponseEntity<>(member, HttpStatus.OK);
	}


}
