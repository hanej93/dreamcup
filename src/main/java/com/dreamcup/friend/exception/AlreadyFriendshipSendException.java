package com.dreamcup.friend.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class AlreadyFriendshipSendException extends CustomException {

	private static final String MESSAGE = "이미 친구 요청을 보냈습니다.";

	public AlreadyFriendshipSendException() {
		super(MESSAGE);
	}

	public AlreadyFriendshipSendException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
