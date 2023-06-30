package com.dreamcup.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	@EntityGraph(attributePaths = "authorities")
	Optional<Member> findWithAuthoritiesByUsername(String username);

	@EntityGraph(attributePaths = "authorities")
	Optional<Member> findWithAuthoritiesById(Long id);

	boolean existsByNicknameAndNameTag(String nickname, String nameTag);

	boolean existsByUsername(String username);
}
