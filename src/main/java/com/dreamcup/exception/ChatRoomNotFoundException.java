package com.dreamcup.exception;

import org.springframework.http.HttpStatus;

public class ChatRoomNotFoundException extends CustomException {

	private static final String MESSAGE = "채팅방을 찾을 수 없습니다.";

	public ChatRoomNotFoundException() {
		super(MESSAGE);
	}

	public ChatRoomNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.NOT_FOUND.value();
	}

}
