package com.dreamcup.config.jwt.provider;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.dreamcup.config.auth.LoginUser;
import com.dreamcup.config.jwt.config.JwtConfigProperties;
import com.dreamcup.member.code.AuthorityEnum;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenProvider {

	public static final String AUTHORITIES_KEY = "auth";

	public static String generateTokenWithPrefix(LoginUser loginUser) {

		String authorities = getMemberAuthorities(loginUser);

		String jwtToken = Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setSubject(loginUser.getMember().getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JwtConfigProperties.EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, JwtConfigProperties.SECRET)
			.claim(AUTHORITIES_KEY, authorities)
			.compact();
		return JwtConfigProperties.TOKEN_PREFIX + jwtToken;
	}

	public static boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(JwtConfigProperties.SECRET).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.error("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.error("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	public static Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(JwtConfigProperties.SECRET)
			.parseClaimsJws(token).getBody();

		String username = claims.getSubject();
		String[] authorities = claims.get(AUTHORITIES_KEY).toString().split(",");

		LoginUser loginUser = getLoginUser(username, authorities);

		return new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
	}

	private static LoginUser getLoginUser(String username, String[] splitAuthority) {
		Member member = Member.builder()
			.username(username)
			.build();

		Set<MemberAuthority> memberAuthorities = Arrays.stream(splitAuthority)
			.map(auth -> new MemberAuthority(member, AuthorityEnum.valueOf(auth)))
			.collect(Collectors.toSet());

		member.addMemberAuthorities(memberAuthorities);

		LoginUser loginUser = new LoginUser(member);
		return loginUser;
	}

	private static String getMemberAuthorities(LoginUser loginUser) {
		return loginUser.getAuthorities().stream()
			.map(auth -> auth.getAuthority())
			.collect(Collectors.joining(","));
	}

}
