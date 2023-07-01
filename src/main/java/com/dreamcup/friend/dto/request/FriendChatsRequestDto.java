package com.dreamcup.friend.dto.request;

import static java.lang.Math.*;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendChatsRequestDto {

	private static final int MAX_SIZE = 2000;

	@NotNull
	private Long senderId;
	@NotNull
	private Long receiverId;

	@Builder.Default
	private Integer page = 0;
	@Builder.Default
	private Integer size = 10;

	public long getOffset() {
		return (long) max(0, page) * min(size, MAX_SIZE);
	}

}
