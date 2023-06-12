package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class FriendshipNotFoundException extends CustomException {

	private static final String MESSAGE = "친구 요청 정보를 찾을 수 없습니다.";

	public FriendshipNotFoundException() {
		super(MESSAGE);
	}

	public FriendshipNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
