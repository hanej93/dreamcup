package com.dreamcup.config;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.requestMatchers("/css/**", "/images/**", "/js/**","/webjars/**","/upload/**", "/fonts/**", "*.ico")
			.requestMatchers(toH2Console());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
				authorizationManagerRequestMatcherRegistry
					.requestMatchers("/auth/login").permitAll()
					.requestMatchers("/auth/signup").permitAll()
					.requestMatchers("/user").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/admin").access(new WebExpressionAuthorizationManager("hasRole('ADMIN')"))
					.anyRequest().authenticated();
			})
			.formLogin(httpSecurityFormLoginConfigurer -> {
				httpSecurityFormLoginConfigurer
					.loginPage("/auth/login")
					.loginProcessingUrl("/auth/login")
					.usernameParameter("username")
					.passwordParameter("password")
					.defaultSuccessUrl("/")
					.permitAll();
			})
			.logout(Customizer.withDefaults())
			.rememberMe(httpSecurityRememberMeConfigurer -> {
				httpSecurityRememberMeConfigurer
					.rememberMeParameter("remember")
					.alwaysRemember(false)
					.tokenValiditySeconds(3600 * 24 * 30);
			});

		return http.build();
	}

	@Bean
	public UserDetailsService users() {
		User.UserBuilder users = User.withDefaultPasswordEncoder();
		UserDetails user = users
			.username("user")
			.password("1234")
			.roles("USER")
			.build();
		UserDetails admin = users
			.username("admin")
			.password("1234")
			.roles("USER", "ADMIN")
			.build();
		return new InMemoryUserDetailsManager(user, admin);
	}

}
