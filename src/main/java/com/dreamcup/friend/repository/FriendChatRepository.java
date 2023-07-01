package com.dreamcup.friend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.friend.entity.FriendChat;
import com.dreamcup.friend.repository.custom.FriendChatRepositoryCustom;
import com.dreamcup.member.entity.Participant;

public interface FriendChatRepository extends JpaRepository<FriendChat, Long>, FriendChatRepositoryCustom {
	List<FriendChat> findBySenderAndReceiver(Participant sender, Participant receiver);

}
