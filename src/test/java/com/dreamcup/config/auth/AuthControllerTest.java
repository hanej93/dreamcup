package com.dreamcup.config.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("토큰 발급 테스트")
	void login() throws Exception {
	    // given
		LoginRequestDto requestDto = LoginRequestDto.builder()
			.username("user")
			.password("1234")
			.build();

		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isOk())
			.andDo(print());
	}

}