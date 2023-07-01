package com.dreamcup.friend.repository.custom.impl;

import static com.dreamcup.friend.entity.QFriendship.*;
import static com.dreamcup.member.entity.QMember.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.dreamcup.friend.dto.request.FriendsSearchRequestDto;
import com.dreamcup.friend.dto.respoonse.FriendResponseDto;
import com.dreamcup.friend.dto.respoonse.QFriendResponseDto;
import com.dreamcup.friend.repository.custom.FriendshipRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class FriendshipRepositoryImpl implements FriendshipRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<FriendResponseDto> findPagedFriends(FriendsSearchRequestDto requestDto) {
		List<FriendResponseDto> content = jpaQueryFactory
			.select(new QFriendResponseDto(
				member.id,
				member.nickname
			))
			.from(friendship)
			.join(friendship.friend, member)
			.where(
				getAcceptedCondition(requestDto.getAccepted())
			)
			.limit(requestDto.getSize())
			.offset(requestDto.getOffset())
			.orderBy(member.nickname.asc())
			.fetch();

		Long total = jpaQueryFactory
			.select(friendship.count())
			.from(friendship)
			.where(
				friendship.accepted.eq(true)
			)
			.fetchOne();

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression getAcceptedCondition(Boolean accepted) {
		if (accepted == null) {
			return null;
		}

		return friendship.accepted.eq(accepted);
	}

}