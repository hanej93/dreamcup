package com.dreamcup.friend.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.friend.dto.request.FriendsSearchRequestDto;
import com.dreamcup.friend.dto.request.FriendshipAcceptRequestDto;
import com.dreamcup.friend.dto.request.FriendshipSendRequestDto;
import com.dreamcup.friend.dto.respoonse.FriendResponseDto;
import com.dreamcup.friend.entity.Friendship;
import com.dreamcup.member.entity.Member;
import com.dreamcup.friend.exception.AlreadyFriendshipException;
import com.dreamcup.friend.exception.AlreadyFriendshipSendException;
import com.dreamcup.friend.exception.FriendshipNotFoundException;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.friend.repository.FriendshipRepository;
import com.dreamcup.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {

	private final FriendshipRepository friendshipRepository;
	private final MemberRepository memberRepository;

	// 친구 요청
	@Transactional
	public Long sendFriendRequest(FriendshipSendRequestDto requestDto) {
		requestDto.validateFriendRequest();

		friendshipRepository.findByMemberIdAndFriendId(
				requestDto.getMemberId(), requestDto.getFriendId())
			.ifPresent(friendship -> {
				if (friendship.isAccepted()) {
					throw new AlreadyFriendshipException();
				} else {
					throw new AlreadyFriendshipSendException();
				}
			});

		Member member = memberRepository.findById(requestDto.getMemberId())
			.orElseThrow(UserNotFoundException::new);
		Member friend = memberRepository.findById(requestDto.getFriendId())
			.orElseThrow(UserNotFoundException::new);

		Friendship friendShip = Friendship.builder()
			.member(member)
			.friend(friend)
			.accepted(false)
			.build();

		friendshipRepository.save(friendShip);

		// todo: notice 발송

		return friendShip.getId();
	}

	// 친구 요청 수락
	@Transactional
	public Long acceptFriendRequest(FriendshipAcceptRequestDto requestDto) {
		Friendship friendShip = friendshipRepository.findById(requestDto.getFriendShipId())
			.orElseThrow(FriendshipNotFoundException::new);
		if (friendShip.isAccepted()) {
			throw new AlreadyFriendshipSendException();
		}

		friendShip.setAccepted(true);

		Friendship reverseFriendship = Friendship.builder()
			.member(friendShip.getFriend())
			.friend(friendShip.getMember())
			.accepted(true)
			.build();
		friendshipRepository.save(reverseFriendship);

		// todo: notice 발송

		return friendShip.getId();
	}

	// 친구 목록 조회
	public Page<FriendResponseDto> getFriends(FriendsSearchRequestDto requestDto) {
		return friendshipRepository.findPagedFriends(requestDto);
	}

	// 등록된 친구 수 조회
	public Long countAcceptedFriends(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(UserNotFoundException::new);
		return friendshipRepository.countByMemberAndAccepted(member, true);
	}
}
