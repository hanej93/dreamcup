package com.dreamcup.member.exception;

import org.springframework.http.HttpStatus;

import com.dreamcup.common.exception.CustomException;

public class ChatRoomParticipantNotFoundException extends CustomException {

	private static final String MESSAGE = "채팅방 참여자를 찾을 수 없습니다.";

	public ChatRoomParticipantNotFoundException() {
		super(MESSAGE);
	}

	public ChatRoomParticipantNotFoundException(Throwable cause) {
		super(MESSAGE, cause);
	}

	@Override
	public int getStatusCode() {
		return HttpStatus.BAD_REQUEST.value();
	}
}
