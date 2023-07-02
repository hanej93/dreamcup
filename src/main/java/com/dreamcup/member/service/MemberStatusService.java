package com.dreamcup.member.service;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.config.jwt.provider.JwtTokenProvider;
import com.dreamcup.friend.config.FriendExchangeConfig;
import com.dreamcup.friend.entity.Friendship;
import com.dreamcup.friend.repository.FriendshipRepository;
import com.dreamcup.member.code.MemberStatusEnum;
import com.dreamcup.member.dto.response.MemberStatusDto;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberStatus;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.mapper.MemberStatusMapper;
import com.dreamcup.member.repository.MemberRepository;
import com.dreamcup.member.repository.MemberStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberStatusService {

	private final MemberStatusRepository memberStatusRepository;
	private final MemberRepository memberRepository;
	private final FriendshipRepository friendshipRepository;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	public void updateMemberStatus(String jwtToken, MemberStatusEnum status) {
		// jwtToken 으로 username 조회
		String username = JwtTokenProvider.getLoginUsername(jwtToken);

		// username으로 유저 조회
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(UserNotFoundException::new);

		MemberStatus memberStatus = MemberStatus.builder()
			.member(member)
			.status(status)
			.build();
		memberStatusRepository.save(memberStatus);

		MemberStatusDto memberStatusDto = MemberStatusMapper.INSTANCE.toMemberStatusDto(memberStatus);
		sendMyStatusToFriends(memberStatusDto, member);
	}

	public void sendMyStatusToFriends(MemberStatusDto memberStatusDto, Member member) {
		// 유저와 등록된 친구 조회
		List<Friendship> friends = friendshipRepository.findWithFriendByMember(member);
		
		// 친구들에게 나의 상태 전송
		friends.forEach(friend -> {
			String routingKey = "status." + friend.getFriend().getId();
			rabbitTemplate.convertAndSend(FriendExchangeConfig.FRIEND_EXCHANGE_NAME, routingKey, memberStatusDto);
		});
	}

}
