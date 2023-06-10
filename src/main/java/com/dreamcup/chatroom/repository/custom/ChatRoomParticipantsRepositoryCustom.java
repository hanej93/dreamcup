package com.dreamcup.chatroom.repository.custom;

public interface ChatRoomParticipantsRepositoryCustom {

    boolean existsByPrivateCodeAndParticipantId(String privateCode, Long participantId);

}
