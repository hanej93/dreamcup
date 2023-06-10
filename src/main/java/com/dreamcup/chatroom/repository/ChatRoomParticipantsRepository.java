package com.dreamcup.chatroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.chatroom.entity.ChatRoomParticipants;

public interface ChatRoomParticipantsRepository extends JpaRepository<ChatRoomParticipants, Long> {

	boolean existsByChatRoomIdAndParticipantId(Long chatRoomId, Long participantId);

}
