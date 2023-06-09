package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class UserNotFoundException extends CustomException {

	private static final String MESSAGE = "사용자를 찾을 수 없습니다.";

	public UserNotFoundException() {
		super(MESSAGE);
	}

	public UserNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}
}
