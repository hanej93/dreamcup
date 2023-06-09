package com.dreamcup.chatroom.dto.response;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private String title;
    private Integer maxUserCount;
    private Integer currentUserCount;
    private LocalDateTime updatedDate;

    // todo:
    // chatRoomId
    // maxUserCount
    // currentUserCount
    // ....

    @QueryProjection
    public ChatRoomResponseDto(Long chatRoomId, String title, Integer maxUserCount, Integer currentUserCount,
        LocalDateTime updatedDate) {
        this.chatRoomId = chatRoomId;
        this.title = title;
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
