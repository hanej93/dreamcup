package com.dreamcup.member.dto.request;

import static java.lang.Math.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendsSearchRequestDto {

	private static final int MAX_SIZE = 2000;

	@Builder.Default
	private Integer page = 0;
	@Builder.Default
	private Integer size = 10;
	private Long memberId;

	public long getOffset() {
		return (long) max(0, page) * min(size, MAX_SIZE);
	}
}
