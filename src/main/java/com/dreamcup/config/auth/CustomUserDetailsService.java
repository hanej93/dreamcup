package com.dreamcup.config.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
		return userRepository.findWithAuthoritiesByUsername(username)
			.map(user -> createUser(username, user))
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	private User createUser(String username, Member member) {
		if (!member.isActivated()) {
			throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
		}

		List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
			.map(authority -> new SimpleGrantedAuthority(authority.getId().getAuthority().name()))
			.collect(Collectors.toList());

		return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
	}
}
