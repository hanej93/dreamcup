package com.dreamcup.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dreamcup.config.jwt.handler.JwtAccessDeniedHandler;
import com.dreamcup.config.jwt.handler.JwtAuthenticationEntryPoint;
import com.dreamcup.config.jwt.filter.JwtAuthenticationFilter;
import com.dreamcup.config.jwt.filter.JwtAuthorizationFilter;
import com.dreamcup.config.jwt.config.JwtConfigProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/css/**", "/images/**", "/js/**","/webjars/**","/upload/**", "/fonts/**", "*.ico")
			.requestMatchers(toH2Console());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.headers(httpSecurityHeadersConfigurer ->
				httpSecurityHeadersConfigurer
					.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
			)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(configurationSource()))

			.sessionManagement(httpSecuritySessionManagementConfigurer ->
				httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)

			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)

			.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			)

			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
				authorizationManagerRequestMatcherRegistry
					.requestMatchers("/api/login", "/api/signup").permitAll()
					.requestMatchers("/user").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/admin").access(new WebExpressionAuthorizationManager("hasRole('ADMIN')"))
					.anyRequest().authenticated();
			})

			.apply(new CustomSecurityFilterManager());

		return http.build();
	}

	public class CustomSecurityFilterManager extends
		AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
		@Override
		public void configure(HttpSecurity builder) throws Exception {
			AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
			builder.addFilter(new JwtAuthenticationFilter(authenticationManager));
			builder.addFilter(new JwtAuthorizationFilter(authenticationManager));
			super.configure(builder);
		}
	}

	public CorsConfigurationSource configurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedOriginPattern("*");
		configuration.setAllowCredentials(true);
		configuration.addExposedHeader(JwtConfigProperties.HEADER);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
