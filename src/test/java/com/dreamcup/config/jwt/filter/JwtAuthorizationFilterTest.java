package com.dreamcup.config.jwt.filter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.dreamcup.config.auth.LoginUser;
import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.config.jwt.provider.JwtTokenProvider;
import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberAuthority;

@AutoConfigureMockMvc
@SpringBootTest
class JwtAuthorizationFilterTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("인가 성공")
	void successAuthorization() throws Exception {
	    // given
		Member member = Member.builder()
			.username("test")
			.build();
		MemberAuthority memberAuthority = new MemberAuthority(member, AuthorityEnum.ROLE_USER);
		member.addMemberAuthorities(memberAuthority);

		LoginUser loginUser = new LoginUser(member);
		String bearerToken = JwtTokenProvider.generateTokenWithPrefix(loginUser);

		// expected
		mockMvc.perform(get("/user")
				.header(JwtConfigProperties.HEADER, bearerToken)
			)
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	@DisplayName("비회원 인가 필요")
	void failAuthorization() throws Exception {
		// given

		// expected
		mockMvc.perform(get("/user")
			)
			.andExpect(status().isUnauthorized())
			.andDo(print());
	}

	@Test
	@DisplayName("권한 부족")
	void accessDenied() throws Exception {
		// given
		Member member = Member.builder()
			.username("test")
			.build();
		MemberAuthority memberAuthority = new MemberAuthority(member, AuthorityEnum.ROLE_USER);
		member.addMemberAuthorities(memberAuthority);

		LoginUser loginUser = new LoginUser(member);
		String bearerToken = JwtTokenProvider.generateTokenWithPrefix(loginUser);

		// expected
		mockMvc.perform(get("/admin")
				.header(JwtConfigProperties.HEADER, bearerToken)
			)
			.andExpect(status().isForbidden())
			.andDo(print());
	}


}