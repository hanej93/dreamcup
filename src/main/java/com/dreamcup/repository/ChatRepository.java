package com.dreamcup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
