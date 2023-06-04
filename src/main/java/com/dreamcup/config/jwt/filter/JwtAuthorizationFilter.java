package com.dreamcup.config.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.config.jwt.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException, ServletException {
		log.debug("JwtAuthorizationFilter.doFilterInternal");
		String jwtToken = getJwtTokenFromRequest(request);

		if (StringUtils.hasText(jwtToken) && JwtTokenProvider.validateToken(jwtToken)) {
			Authentication authentication = JwtTokenProvider.getAuthentication(jwtToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}

	private String getJwtTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtConfigProperties.HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConfigProperties.TOKEN_PREFIX)) {
			return bearerToken.replace(JwtConfigProperties.TOKEN_PREFIX, "");
		}
		return null;
	}
}
