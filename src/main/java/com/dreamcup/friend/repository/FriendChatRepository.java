package com.dreamcup.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.friend.entity.FriendChat;

public interface FriendChatRepository extends JpaRepository<FriendChat, Long> {
}
