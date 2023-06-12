package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class AlreadyFriendshipException extends CustomException {

	private static final String MESSAGE = "이미 친구인 유저입니다.";

	public AlreadyFriendshipException() {
		super(MESSAGE);
	}

	public AlreadyFriendshipException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
