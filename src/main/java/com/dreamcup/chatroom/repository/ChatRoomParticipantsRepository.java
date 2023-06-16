package com.dreamcup.chatroom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.chatroom.entity.Chat;
import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.entity.ChatRoomParticipants;
import com.dreamcup.chatroom.repository.custom.ChatRoomParticipantsRepositoryCustom;
import com.dreamcup.member.entity.Participant;

public interface ChatRoomParticipantsRepository extends JpaRepository<ChatRoomParticipants, Long>, ChatRoomParticipantsRepositoryCustom {

	boolean existsByChatRoomIdAndParticipantId(Long chatRoomId, Long participantId);

	Optional<ChatRoomParticipants> findByChatRoomAndParticipant(ChatRoom chatRoom, Participant participant);
}
