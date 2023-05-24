package com.dreamcup.dto.request;

import com.dreamcup.entity.ChatRoom;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomSaveRequestDto {

	@NotBlank(message = "제목을 입력해주세요.")
    private String title;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .title(title)
                .build();
    }

}
