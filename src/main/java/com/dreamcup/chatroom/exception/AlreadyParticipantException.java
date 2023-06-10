package com.dreamcup.chatroom.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class AlreadyParticipantException extends CustomException {

	private static final String MESSAGE = "이미 채팅방에 입장하였습니다.";

	public AlreadyParticipantException() {
		super(MESSAGE);
	}

	public AlreadyParticipantException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
