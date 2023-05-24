package com.dreamcup.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.common.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
