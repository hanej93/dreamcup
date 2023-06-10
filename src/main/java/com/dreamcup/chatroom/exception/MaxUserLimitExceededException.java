package com.dreamcup.chatroom.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class MaxUserLimitExceededException extends CustomException {

	private static final String MESSAGE = "채팅방 최대 참여 인원을 초과하였습니다.";

	public MaxUserLimitExceededException() {
		super(MESSAGE);
	}

	public MaxUserLimitExceededException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
