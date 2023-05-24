package com.dreamcup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
