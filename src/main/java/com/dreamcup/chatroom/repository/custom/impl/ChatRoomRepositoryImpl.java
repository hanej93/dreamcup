package com.dreamcup.chatroom.repository.custom.impl;

import static com.dreamcup.chatroom.entity.QChatRoom.*;
import static com.dreamcup.member.entity.QParticipant.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;
import com.dreamcup.chatroom.dto.response.QChatRoomResponseDto;
import com.dreamcup.chatroom.repository.custom.ChatRoomRepositoryCustom;
import com.querydsl.core.types.ExpressionException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ChatRoomResponseDto> getPagenatedList(ChatRoomSearchRequestDto requestDto) {
        return jpaQueryFactory.select(new QChatRoomResponseDto(chatRoom.title, chatRoom.updatedDate))
                .from(chatRoom)
                .where(
                        keywordContains(requestDto.getSchType(), requestDto.getKeyword())
                )
                .limit(requestDto.getSize())
                .offset(requestDto.getOffset())
                .orderBy(chatRoom.chatRoomId.desc())
                .fetch();
    }

	@Override
	public Page<ChatRoomResponseDto> getPagedChatRooms(ChatRoomSearchRequestDto requestDto) {

		List<ChatRoomResponseDto> content = jpaQueryFactory
			.select(new QChatRoomResponseDto(
				chatRoom.chatRoomId,
				chatRoom.title,
				chatRoom.maxUserCount,
				ExpressionUtils.as(chatRoom.participants.size(), "currentUserCount"),
				chatRoom.updatedDate
			))
			.from(chatRoom)
			.where(
				keywordContains(requestDto.getSchType(), requestDto.getKeyword())
			)
			.limit(requestDto.getSize())
			.offset(requestDto.getOffset())
			.orderBy(chatRoom.chatRoomId.desc())
			.fetch();

		Long total = jpaQueryFactory
			.select(chatRoom.count())
			.from(chatRoom)
			.from(chatRoom)
			.where(
				keywordContains(requestDto.getSchType(), requestDto.getKeyword())
			)
			.fetchOne();

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression keywordContains(String schType, String keyword) {
		if ("title".equals(schType)) {
			return StringUtils.hasText(keyword) ? chatRoom.title.contains(keyword) : null;
		}
		return null;
    }
}
