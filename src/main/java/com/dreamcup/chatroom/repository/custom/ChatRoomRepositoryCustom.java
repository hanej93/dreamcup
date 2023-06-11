package com.dreamcup.chatroom.repository.custom;

import com.dreamcup.chatroom.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.chatroom.dto.response.ChatRoomResponseDto;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ChatRoomRepositoryCustom {

    Page<ChatRoomResponseDto> getPagedChatRooms(ChatRoomSearchRequestDto chatRoomSearchRequestDto);

}
