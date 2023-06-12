package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class NotEqualsMemberAndFriendException extends CustomException {

	private static final String MESSAGE = "요청한 유저와 요청받은 유저가 같을 수 없습니다.";

	public NotEqualsMemberAndFriendException() {
		super(MESSAGE);
	}

	public NotEqualsMemberAndFriendException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
