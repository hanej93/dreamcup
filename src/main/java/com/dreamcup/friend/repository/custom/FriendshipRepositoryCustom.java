package com.dreamcup.friend.repository.custom;

import org.springframework.data.domain.Page;

import com.dreamcup.friend.dto.request.FriendsSearchRequestDto;
import com.dreamcup.friend.dto.respoonse.FriendResponseDto;

public interface FriendshipRepositoryCustom {

    Page<FriendResponseDto> findPagedFriends(FriendsSearchRequestDto requestDto);

}
