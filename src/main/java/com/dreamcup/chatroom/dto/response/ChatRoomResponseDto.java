package com.dreamcup.chatroom.dto.response;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomResponseDto {

    private String title;
    private LocalDateTime updatedDate;

    @QueryProjection
    public ChatRoomResponseDto(String title, LocalDateTime updatedDate) {
        this.title = title;
        this.updatedDate = updatedDate;
    }

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.title = chatRoom.getTitle();
        this.updatedDate = chatRoom.getUpdatedDate();
    }
}
