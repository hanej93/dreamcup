package com.dreamcup.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidNicknameRequestDto {

	@NotEmpty
	private String nickname;

	@NotEmpty
	private String tagName;
}
