package com.dreamcup.member.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.dreamcup.config.QueryDslConfig;
import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberAuthority;
import com.dreamcup.member.code.AuthorityEnum;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Import({QueryDslConfig.class})
@DataJpaTest
class MemberRepositoryTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MemberRepository memberRepository;

	@BeforeEach
	void setup() {
		memberRepository.deleteAll();
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

		MemberAuthority memberAuthority = new MemberAuthority();
		memberAuthority.addMemberAuthority(member, AuthorityEnum.ROLE_USER);

		em.persist(member);

		// when
		Member findMember = memberRepository.findWithAuthoritiesByUsername(member.getUsername())
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		// then
		assertThat(findMember).extracting("username").isEqualTo("user");
		assertThat(findMember).extracting("password").isEqualTo("1234");
		assertThat(findMember).extracting("nickname").isEqualTo("user-nick");
		assertThat(findMember.isActivated()).isTrue();
		assertThat(findMember.getAuthorities()).extracting("id").extracting("authority").containsExactly(AuthorityEnum.ROLE_USER);
	}

}