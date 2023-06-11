package com.dreamcup.chatroom.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomSearchRequestDto {

    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 0;
    @Builder.Default
    private Integer size = 10;

    private String schType;
    private String keyword;
    private boolean isPublicOnly;

    public long getOffset() {
        return (long) max(0, page) * min(size, MAX_SIZE);
    }

}
