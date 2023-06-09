package com.dreamcup.chatroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.chatroom.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
