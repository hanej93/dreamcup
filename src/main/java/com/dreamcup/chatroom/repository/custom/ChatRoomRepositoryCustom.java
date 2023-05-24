package com.dreamcup.chatroom.repository.custom;

import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomResponseDto> getPagenatedList(ChatRoomSearchRequestDto chatRoomSearchRequestDto);

}
