package com.dreamcup.chatroom.repository;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.repository.custom.ChatRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

}
