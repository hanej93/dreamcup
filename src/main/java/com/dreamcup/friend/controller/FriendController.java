package com.dreamcup.friend.controller;

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

import com.dreamcup.friend.service.FriendService;
import com.dreamcup.friend.dto.request.FriendsSearchRequestDto;
import com.dreamcup.friend.dto.request.FriendshipAcceptRequestDto;
import com.dreamcup.friend.dto.request.FriendshipSendRequestDto;
import com.dreamcup.friend.dto.respoonse.FriendResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FriendController {

	private final FriendService friendService;

	// 친구 요청
	@PostMapping("/friendship")
	public ResponseEntity<Long> sendFriendRequest(@RequestBody FriendshipSendRequestDto requestDto) {
		Long id = friendService.sendFriendRequest(requestDto);
		return new ResponseEntity(id, HttpStatus.OK);
	}

	// 친구 수락
	@PostMapping("/friendship/accept")
	public ResponseEntity<Long> acceptFriendRequest(@RequestBody FriendshipAcceptRequestDto requestDto) {
		Long id = friendService.acceptFriendRequest(requestDto);
		return new ResponseEntity(id, HttpStatus.OK);
	}

	// 친구 목록 조회
	@GetMapping("/friendship")
	public ResponseEntity<Page<FriendResponseDto>> getFriendList(@ModelAttribute FriendsSearchRequestDto requestDto) {
		Page<FriendResponseDto> friends = friendService.getFriends(requestDto);
		return new ResponseEntity(friends, HttpStatus.OK);
	}

	// todo: 요청 친구 수 추가 필요
	// 등록된 친구 수 조회
	@GetMapping("/friendship/count/{memberId}")
	public ResponseEntity<Long> countFriends(@PathVariable Long memberId) {
		Long count = friendService.countAcceptedFriends(memberId);
		return ResponseEntity.ok(count);
	}

}
