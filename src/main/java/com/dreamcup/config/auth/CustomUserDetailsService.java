package com.dreamcup.config.auth;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.member.entity.Member;
import com.dreamcup.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Member member = userRepository.findWithAuthoritiesByUsername(username)
			.orElseThrow(() -> new InternalAuthenticationServiceException(username + " -> 데이터베이스에서 찾을 수 없습니다."));

		return new LoginUser(member);
	}

}
