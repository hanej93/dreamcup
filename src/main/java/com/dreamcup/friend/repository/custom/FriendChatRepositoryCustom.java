package com.dreamcup.friend.repository.custom;

import java.util.List;

import com.dreamcup.friend.dto.request.FriendChatsRequestDto;
import com.dreamcup.friend.entity.FriendChat;

public interface FriendChatRepositoryCustom {

	List<FriendChat> findChatsWithFriend(FriendChatsRequestDto requestDto);

}
