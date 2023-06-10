package com.dreamcup.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.chatroom.entity.ChatRoomParticipants;
import com.dreamcup.chatroom.repository.custom.ChatRoomParticipantsRepositoryCustom;

public interface ChatRoomParticipantsRepository extends JpaRepository<ChatRoomParticipants, Long>, ChatRoomParticipantsRepositoryCustom {

	boolean existsByChatRoomIdAndParticipantId(Long chatRoomId, Long participantId);

}
