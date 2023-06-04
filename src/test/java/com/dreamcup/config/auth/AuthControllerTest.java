package com.dreamcup.config.auth;

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.config.jwt.dto.LoginRequestDto;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("토큰 발급 테스트")
	void login() throws Exception {
	    // given
		MemberSignupRequestDto signupRequestDto = MemberSignupRequestDto.builder()
			.username("user1")
			.password("1234")
			.nickname("user-nick")
			.build();

		memberService.signup(signupRequestDto);

		LoginRequestDto requestDto = LoginRequestDto.builder()
			.username("user1")
			.password("1234")
			.build();

		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.header().exists(JwtConfigProperties.HEADER))
			.andDo(print());
	}

	// @Test
	// @DisplayName("토큰 발급 테스트(회원이 없는 경우")
	// void loginNotFoundMember() throws Exception {
	// 	// given
	// 	LoginRequestDto requestDto = LoginRequestDto.builder()
	// 		.username("user1")
	// 		.password("1234")
	// 		.build();
	//
	// 	String request = objectMapper.writeValueAsString(requestDto);
	//
	// 	// expected
	// 	mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
	// 			.contentType(APPLICATION_JSON)
	// 			.content(request)
	// 		)
	// 		.andExpect(status().isInternalServerError())
	// 		.andDo(print());
	// }

}