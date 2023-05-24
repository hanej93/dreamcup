package com.dreamcup.member.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.dreamcup.config.QueryDslConfig;
import com.dreamcup.member.entity.Authority;
import com.dreamcup.member.entity.Member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Import({QueryDslConfig.class})
@DataJpaTest
class MemberRepositoryTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@BeforeEach
	void setup() {
		memberRepository.deleteAll();

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();
		authorityRepository.save(authority);
	}

	@Test
	@DisplayName("유저, 권한 조회(이름)")
	public void findOneWithAuthoritiesByUsername() {
		// given
		Member member = Member.builder()
			.username("user")
			.password("1234")
			.nickname("user-nick")
			.activated(true)
			.build();

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();
		member.getAuthorities().add(authority);

		em.persist(member);

		// when
		Member findMember = memberRepository.findOneWithAuthoritiesByUsername(member.getUsername())
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		// then
		assertThat(findMember).extracting("username").isEqualTo("user");
		assertThat(findMember).extracting("password").isEqualTo("1234");
		assertThat(findMember).extracting("nickname").isEqualTo("user-nick");
		assertThat(findMember.isActivated()).isTrue();
		assertThat(findMember.getAuthorities()).extracting("authorityName").containsExactly("ROLE_USER");
	}

}