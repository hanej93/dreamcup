package com.dreamcup.config.jwt.filter;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.config.jwt.dto.LoginRequestDto;
import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.repository.MemberRepository;
import com.dreamcup.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class JwtAuthenticationFilterTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	public void setup() {
		memberRepository.deleteAll();

		MemberSignupRequestDto requestDto = MemberSignupRequestDto.builder()
			.username("test")
			.password("1234")
			.nickname("test-nick")
			.build();

		memberService.signup(requestDto);
	}

	@Test
	@DisplayName("로그인 성공")
	void loginSuccess() throws Exception {
	    // given
		LoginRequestDto loginRequestDto = LoginRequestDto.builder()
			.username("test")
			.password("1234")
			.build();
		String request = objectMapper.writeValueAsString(loginRequestDto);

		// when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andDo(print());
		String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtConfigProperties.HEADER);

	    // then
		resultActions.andExpect(status().isOk());
		assertThat(jwtToken).isNotNull();
		assertThat(jwtToken).startsWith(JwtConfigProperties.TOKEN_PREFIX);
		resultActions.andExpect(jsonPath("$.username").value("test"));
	}
	
	@Test
	@DisplayName("로그인 실패")
	void loginFail() throws Exception {
		// given
		LoginRequestDto loginRequestDto = LoginRequestDto.builder()
			.username("test")
			.password("12345")
			.build();
		String request = objectMapper.writeValueAsString(loginRequestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isUnauthorized())
			.andDo(print());
	}

}