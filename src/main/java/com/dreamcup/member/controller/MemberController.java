package com.dreamcup.member.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.member.dto.request.FriendsSearchRequestDto;
import com.dreamcup.member.dto.request.FriendshipAcceptRequestDto;
import com.dreamcup.member.dto.request.FriendshipSendRequestDto;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.dto.response.FriendsResponseDto;
import com.dreamcup.member.dto.response.MemberResponseDto;
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

	// 회원 정보 조회
	@GetMapping("/members/{memberId}")
	public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {
		MemberResponseDto member = memberService.findMember(memberId);
		return new ResponseEntity<>(member, HttpStatus.OK);
	}

	@PostMapping("/friendship")
	public ResponseEntity<Long> sendFriendRequest(@RequestBody FriendshipSendRequestDto requestDto) {
		Long id = memberService.sendFriendRequest(requestDto);
		return new ResponseEntity(id, HttpStatus.OK);
	}

	@PostMapping("/friendship/accept")
	public ResponseEntity<Long> acceptFriendRequest(@RequestBody FriendshipAcceptRequestDto requestDto) {
		Long id = memberService.acceptFriendRequest(requestDto);
		return new ResponseEntity(id, HttpStatus.OK);
	}

	@GetMapping("/friendship")
	public ResponseEntity<Page<FriendsResponseDto>> getFriendList(@ModelAttribute FriendsSearchRequestDto requestDto) {
		Page<FriendsResponseDto> friends = memberService.getFriends(requestDto);
		return new ResponseEntity(friends, HttpStatus.OK);
	}

}
