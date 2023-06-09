package com.dreamcup.chatroom.dto.response;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private String title;
    private String creatorName;
    private Integer maxUserCount;
    private Integer currentUserCount;
    private LocalDateTime updatedDate;

    @QueryProjection
    public ChatRoomResponseDto(Long chatRoomId, String title, String creatorName, Integer maxUserCount,
        Integer currentUserCount, LocalDateTime updatedDate) {
        this.chatRoomId = chatRoomId;
        this.title = title;
        this.creatorName = creatorName;
        this.maxUserCount = maxUserCount;
        this.currentUserCount = currentUserCount;
        this.updatedDate = updatedDate;
    }

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
