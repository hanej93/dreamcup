package com.dreamcup.chatroom.repository.custom.impl;

import static com.dreamcup.chatroom.entity.QChatRoom.*;
import static com.dreamcup.chatroom.entity.QChatRoomParticipants.*;

import com.dreamcup.chatroom.repository.custom.ChatRoomParticipantsRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoomParticipantsRepositoryCustomImpl implements ChatRoomParticipantsRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public boolean existsByPrivateCodeAndParticipantId(String privateCode, Long participantId) {
		return jpaQueryFactory
			.selectOne()
			.from(chatRoomParticipants)
			.join(chatRoomParticipants.chatRoom, chatRoom)
			.where(chatRoom.privateCode.eq(privateCode)
				.and(chatRoomParticipants.participant.id.eq(participantId))
			)
			.fetchFirst() != null;
	}
}
