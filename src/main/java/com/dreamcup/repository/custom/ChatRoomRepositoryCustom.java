package com.dreamcup.repository.custom;

import com.dreamcup.dto.request.ChatRoomSearchRequestDto;
import com.dreamcup.dto.response.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomResponseDto> getPagenatedList(ChatRoomSearchRequestDto chatRoomSearchRequestDto);

}
