package com.dreamcup.member.repository.custom;

import org.springframework.data.domain.Page;

import com.dreamcup.member.dto.request.FriendsSearchRequestDto;
import com.dreamcup.member.dto.response.FriendsResponseDto;

public interface FriendshipRepositoryCustom {

    Page<FriendsResponseDto> findPagedFriends(FriendsSearchRequestDto requestDto);

}
