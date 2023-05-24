package com.dreamcup.config.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {

	private String token;

	public TokenResponseDto(String token) {
		this.token = token;
	}
}
