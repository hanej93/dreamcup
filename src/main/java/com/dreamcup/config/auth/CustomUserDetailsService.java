package com.dreamcup.config.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
		Member member = userRepository.findWithAuthoritiesByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));

		return toUserDetails(member);
	}

	private User toUserDetails(Member member) {
		if (!member.isActivated()) {
			throw new RuntimeException(member.getUsername() + " -> 활성화되어 있지 않습니다.");
		}

		List<String> roles = member.getAuthorities().stream()
			.map(auth -> auth.getId().getAuthority().name())
			.collect(Collectors.toList());

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.createAuthorityList(roles);

		return new User(member.getUsername(), member.getPassword(), grantedAuthorities);
	}
}
