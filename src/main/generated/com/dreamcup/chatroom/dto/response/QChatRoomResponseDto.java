package com.dreamcup.chatroom.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.dreamcup.chatroom.dto.response.QChatRoomResponseDto is a Querydsl Projection type for ChatRoomResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QChatRoomResponseDto extends ConstructorExpression<ChatRoomResponseDto> {

    private static final long serialVersionUID = 686900482L;

    public QChatRoomResponseDto(com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<java.time.LocalDateTime> updatedDate) {
        super(ChatRoomResponseDto.class, new Class<?>[]{String.class, java.time.LocalDateTime.class}, title, updatedDate);
    }

}

