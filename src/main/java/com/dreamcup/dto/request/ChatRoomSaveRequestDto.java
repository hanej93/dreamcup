package com.dreamcup.dto.request;

import com.dreamcup.domain.ChatRoom;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomSaveRequestDto {

    private String title;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .title(title)
                .build();
    }
}
