package com.dreamcup.config.jwt.filter;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.dreamcup.config.auth.CustomUserDetailsService;
import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.config.jwt.provider.JwtTokenProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserDetailsService userDetailsService;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException, ServletException {
		log.debug("JwtAuthorizationFilter.doFilterInternal");
		String jwtToken = getJwtTokenFromRequest(request);

		if (StringUtils.hasText(jwtToken) && JwtTokenProvider.validateToken(jwtToken)) {
			// Authentication authentication = JwtTokenProvider.getAuthentication(jwtToken);
			String loginUsername = JwtTokenProvider.getLoginUsername(jwtToken);
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginUsername);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userDetails, jwtToken, userDetails.getAuthorities());

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
