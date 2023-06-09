package com.dreamcup.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.repository.custom.ChatRoomRepositoryCustom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

}
