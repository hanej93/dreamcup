package com.dreamcup.dto.request;

import com.dreamcup.domain.ChatRoom;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomSaveRequestDto {

	@NotEmpty
    private String title;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .title(title)
                .build();
    }

}
