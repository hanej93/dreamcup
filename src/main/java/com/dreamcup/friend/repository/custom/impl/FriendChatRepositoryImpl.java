package com.dreamcup.friend.repository.custom.impl;

import static com.dreamcup.friend.entity.QFriendChat.*;

import java.util.List;

import com.dreamcup.friend.code.ReadStatus;
import com.dreamcup.friend.dto.request.FriendChatReadUpdateRequestDto;
import com.dreamcup.friend.dto.request.FriendChatsRequestDto;
import com.dreamcup.friend.entity.FriendChat;
import com.dreamcup.friend.repository.custom.FriendChatRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendChatRepositoryImpl implements FriendChatRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<FriendChat> findChatsWithFriend(FriendChatsRequestDto requestDto) {
		Long senderId = requestDto.getSenderId();
		Long receiverId = requestDto.getReceiverId();

		return jpaQueryFactory
			.select(friendChat)
			.from(friendChat)
			.where(
				getSenderIdAndReceiverIdCondition(senderId, receiverId)
					.or(getSenderIdAndReceiverIdCondition(receiverId, senderId))
			)
			.orderBy(friendChat.createdDate.desc())
			.limit(requestDto.getSize())
			.offset(requestDto.getOffset())
			.fetch();
	}

	private static BooleanExpression getSenderIdAndReceiverIdCondition(Long senderId, Long receiverId) {
		return friendChat.sender.id.eq(senderId).and(friendChat.receiver.id.eq(receiverId));
	}

	@Override
	public Long updateChatsAsRead(FriendChatReadUpdateRequestDto requestDto) {
		return jpaQueryFactory.update(friendChat)
			.set(friendChat.readStatus, ReadStatus.READ)
			.where(
				friendChat.sender.id.eq(requestDto.getFriendId()),
				friendChat.receiver.id.eq(requestDto.getMemberId()),
				friendChat.id.loe(requestDto.getLastChatId())
			)
			.execute();
	}
}
