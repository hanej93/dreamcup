package com.dreamcup.dto.response;

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
}
