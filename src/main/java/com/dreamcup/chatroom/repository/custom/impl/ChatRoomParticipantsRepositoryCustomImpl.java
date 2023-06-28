package com.dreamcup.chatroom.repository.custom.impl;

import static com.dreamcup.chatroom.entity.QChatRoom.*;
import static com.dreamcup.chatroom.entity.QChatRoomParticipants.*;

import java.util.List;

import com.dreamcup.chatroom.dto.response.ParticipantsInChatRoomResponseDto;
import com.dreamcup.chatroom.dto.response.QParticipantsInChatRoomResponseDto;
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

	@Override
	public List<ParticipantsInChatRoomResponseDto> getParticipantsByChatRoomId(Long chatRoomId) {
		return jpaQueryFactory.select(new QParticipantsInChatRoomResponseDto(
				chatRoomParticipants.participant.id,
				chatRoomParticipants.participant.nickname
			))
			.from(chatRoomParticipants)
			.where(
				chatRoomParticipants.chatRoom.id.eq(chatRoomId)
			)
			.orderBy(chatRoomParticipants.id.asc())
			.fetch();
	}
}
