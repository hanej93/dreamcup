package com.dreamcup.repository;

import com.dreamcup.entity.ChatRoom;
import com.dreamcup.repository.custom.ChatRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {

}
