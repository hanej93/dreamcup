package com.dreamcup.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/signup")
	public ResponseEntity<Long> signup(@RequestBody @Valid MemberSignupRequestDto requestDto) {
		Long id = memberService.signup(requestDto);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

}
