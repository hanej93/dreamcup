package com.dreamcup.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupRequestDto {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String nickname;
	@NotEmpty
	private String nameTag;

}
