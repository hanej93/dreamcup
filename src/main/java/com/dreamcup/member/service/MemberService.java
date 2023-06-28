package com.dreamcup.member.service;

import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.dto.request.FriendsSearchRequestDto;
import com.dreamcup.member.dto.request.FriendshipAcceptRequestDto;
import com.dreamcup.member.dto.request.FriendshipSendRequestDto;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.dto.response.FriendsResponseDto;
import com.dreamcup.member.dto.response.MemberResponseDto;
import com.dreamcup.member.entity.Friendship;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.exception.AlreadyFriendshipException;
import com.dreamcup.member.exception.AlreadyFriendshipSendException;
import com.dreamcup.member.exception.DuplicateMemberException;
import com.dreamcup.member.exception.FriendshipNotFoundException;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.repository.FriendshipRepository;
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
			.build();
		member.addMemberAuthority(AuthorityEnum.ROLE_USER);

		memberRepository.save(member);

		return member.getId();
	}

	// 회원 정보 조회
	public MemberResponseDto findMember(Long memberId) {
		Member member = memberRepository.findWithAuthoritiesById(memberId)
			.orElseThrow(UserNotFoundException::new);
		return new MemberResponseDto(member);
	}

	// 친구 요청
	@Transactional
	public Long sendFriendRequest(FriendshipSendRequestDto requestDto) {
		requestDto.validateFriendRequest();

		friendShipRepository.findByMemberIdAndFriendId(
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

		friendShipRepository.save(friendShip);

		// todo: notice 발송

		return friendShip.getId();
	}

	// 친구 요청 수락
	@Transactional
	public Long acceptFriendRequest(FriendshipAcceptRequestDto requestDto) {
		Friendship friendShip = friendShipRepository.findById(requestDto.getFriendShipId())
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
		friendShipRepository.save(reverseFriendship);

		// todo: notice 발송

		return friendShip.getId();
	}

	// 친구 목록 조회
	public Page<FriendsResponseDto> getFriends(FriendsSearchRequestDto requestDto) {
		return friendShipRepository.findPagedFriends(requestDto);
	}

}
