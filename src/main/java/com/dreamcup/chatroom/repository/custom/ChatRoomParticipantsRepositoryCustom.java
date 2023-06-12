package com.dreamcup.chatroom.repository.custom;

import java.util.List;

import com.dreamcup.chatroom.dto.response.ParticipantsInChatRoomResponseDto;

public interface ChatRoomParticipantsRepositoryCustom {

    boolean existsByPrivateCodeAndParticipantId(String privateCode, Long participantId);

    List<ParticipantsInChatRoomResponseDto> getParticipantsByChatRoomId(Long chatRoomId);
}
