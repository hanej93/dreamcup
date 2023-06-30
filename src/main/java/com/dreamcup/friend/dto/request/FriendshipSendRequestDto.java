package com.dreamcup.friend.dto.request;

import com.dreamcup.member.exception.NotEqualsMemberAndFriendException;

import lombok.Data;

@Data
public class FriendshipSendRequestDto {

	private Long memberId;
	private Long friendId;

	public void validateFriendRequest() {
		if (memberId.equals(friendId)) {
			throw new NotEqualsMemberAndFriendException();
		}
	}

}
