package com.dreamcup.member.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dreamcup.member.dto.request.MemberSignupRequestDto;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	public void clean() throws Exception {
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입")
	void signup() throws Exception {
		// given
		MemberSignupRequestDto requestDto = MemberSignupRequestDto.builder()
			.username("user")
			.password("1234")
			.nickname("user-nick")
			.build();

		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@Test
	@DisplayName("회원가입(이름 중복)")
	void signupDuplicatedUsername() throws Exception {
		// given
		Member member = Member.builder()
			.username("user")
			.password("1234")
			.nickname("user-nick2")
			.activated(true)
			.build();
		memberRepository.save(member);

		MemberSignupRequestDto requestDto = MemberSignupRequestDto.builder()
			.username("user")
			.password("1234")
			.nickname("user-nick")
			.build();

		String request = objectMapper.writeValueAsString(requestDto);

		// expected
		mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
				.contentType(APPLICATION_JSON)
				.content(request)
			)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("400"))
			.andExpect(jsonPath("$.message").exists())
			.andDo(print());
	}
}