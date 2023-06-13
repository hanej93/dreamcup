package com.dreamcup.chatroom.repository.custom.impl;

import static com.dreamcup.chatroom.entity.QChatRoom.*;

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
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<ChatRoomResponseDto> getPagedChatRooms(ChatRoomSearchRequestDto requestDto) {

		List<ChatRoomResponseDto> content = jpaQueryFactory
			.select(new QChatRoomResponseDto(
				chatRoom.id,
				chatRoom.title,
				chatRoom.creator.nickname,
				chatRoom.maxUserCount,
				ExpressionUtils.as(chatRoom.participants.size(), "currentUserCount"),
				chatRoom.updatedDate
			))
			.from(chatRoom)
			.where(
				getKeywordCondition(requestDto.getSchType(), requestDto.getKeyword()),
				getPublicOnlyCondition(requestDto)
			)
			.limit(requestDto.getSize())
			.offset(requestDto.getOffset())
			.orderBy(chatRoom.id.desc())
			.fetch();

		Long total = jpaQueryFactory
			.select(chatRoom.count())
			.from(chatRoom)
			.where(
				getKeywordCondition(requestDto.getSchType(), requestDto.getKeyword())
			)
			.fetchOne();

		Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getSize());

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression getKeywordCondition(String schType, String keyword) {
		if (!StringUtils.hasText(keyword)) {
			return null;
		}

		if ("title".equals(schType)) {
			return chatRoom.title.contains(keyword);
		}
		return null;
    }

	private BooleanExpression getPublicOnlyCondition(ChatRoomSearchRequestDto requestDto) {
		return requestDto.isOnlyPublic() ? chatRoom.isPrivate.eq(!requestDto.isOnlyPublic()) : null;
	}

}
