package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class DuplicateMemberException extends CustomException {

	private static final String MESSAGE = "이미 회원인 유저입니다.";

	public DuplicateMemberException() {
		super(MESSAGE);
	}

	public DuplicateMemberException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}
}
