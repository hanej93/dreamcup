package com.dreamcup.user.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.dreamcup.config.QueryDslConfig;
import com.dreamcup.user.entity.Authority;
import com.dreamcup.user.entity.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Import({QueryDslConfig.class})
@DataJpaTest
class UserRepositoryTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@BeforeEach
	void setup() {
		userRepository.deleteAll();

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();
		authorityRepository.save(authority);
	}

	@Test
	@DisplayName("유저, 권한 조회(이름)")
	public void findOneWithAuthoritiesByUsername() {
		// given
		User user = User.builder()
			.username("user")
			.password("1234")
			.nickname("user-nick")
			.activated(true)
			.build();

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();
		user.getAuthorities().add(authority);

		em.persist(user);

		// when
		User findUser = userRepository.findOneWithAuthoritiesByUsername(user.getUsername())
			.orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

		// then
		assertThat(findUser).extracting("username").isEqualTo("user");
		assertThat(findUser).extracting("password").isEqualTo("1234");
		assertThat(findUser).extracting("nickname").isEqualTo("user-nick");
		assertThat(findUser.isActivated()).isTrue();
		assertThat(findUser.getAuthorities()).extracting("authorityName").containsExactly("ROLE_USER");
	}

}