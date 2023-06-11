package com.dreamcup.chatroom.dto.response;

import java.time.LocalDateTime;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

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

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getId();
        this.title = chatRoom.getTitle();
        this.creatorName = chatRoom.getCreator().getNickname();
        this.maxUserCount = chatRoom.getMaxUserCount();
        this.currentUserCount = chatRoom.getParticipants().size();
        this.updatedDate = chatRoom.getCreatedDate();
    }
}
