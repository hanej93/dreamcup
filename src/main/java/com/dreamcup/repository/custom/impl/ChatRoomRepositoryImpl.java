package com.dreamcup.repository.custom.impl;

import com.dreamcup.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.dto.response.ChatRoomResponseDto;
import com.dreamcup.dto.response.QChatRoomResponseDto;
import com.dreamcup.repository.custom.ChatRoomRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.dreamcup.entity.QChatRoom.chatRoom;

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

    private BooleanExpression keywordContains(String schType, String keyword) {
		if ("title".equals(schType)) {
			return StringUtils.hasText(keyword) ? chatRoom.title.contains(keyword) : null;
		}
		return null;
    }
}
