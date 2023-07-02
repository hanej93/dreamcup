package com.dreamcup.friend.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReadStatus {
	READ("읽음"), UNREAD("안읽음");

	private final String value;
}
