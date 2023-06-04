package com.dreamcup.config.jwt.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dreamcup.common.util.CustomResponseUtil;
import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.config.jwt.dto.LoginResponseDto;
import com.dreamcup.config.jwt.provider.JwtTokenProvider;
import com.dreamcup.config.jwt.dto.LoginRequestDto;
import com.dreamcup.config.auth.LoginUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final ObjectMapper objectMapper = new ObjectMapper();

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		setFilterProcessesUrl("/api/login");
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		log.debug("JwtAuthenticationFilter.attemptAuthentication");
		try {
			LoginRequestDto loginRequestDto = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestDto.getUsername(), loginRequestDto.getPassword());

			Authentication authenticate = authenticationManager.authenticate(authenticationToken);
			
			return authenticate;
		} catch (IOException e) {
			throw new InternalAuthenticationServiceException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		log.debug("JwtAuthenticationFilter.successfulAuthentication");

		LoginUser loginUser = (LoginUser)authResult.getPrincipal();
		String jwtToken = JwtTokenProvider.generateTokenWithPrefix(loginUser);
		response.addHeader(JwtConfigProperties.HEADER, jwtToken);
		LoginResponseDto loginResponseDto = new LoginResponseDto(loginUser.getMember());
		CustomResponseUtil.success(response, loginResponseDto);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException failed) throws IOException, ServletException {
		log.debug("JwtAuthenticationFilter.unsuccessfulAuthentication");

		CustomResponseUtil.fail(response, "Invalid Username or Password", HttpStatus.UNAUTHORIZED);
	}
}
