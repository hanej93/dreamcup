package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class MemberNotFoundException extends CustomException {

	private static final String MESSAGE = "회원을 찾을 수 없습니다.";

	public MemberNotFoundException() {
		super(MESSAGE);
	}

	public MemberNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}
}
