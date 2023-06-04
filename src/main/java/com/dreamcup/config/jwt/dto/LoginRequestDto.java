package com.dreamcup.config.jwt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDto {

	private String username;
	private String password;

	@Builder
	public LoginRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
