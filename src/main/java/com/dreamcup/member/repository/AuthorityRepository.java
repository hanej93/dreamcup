package com.dreamcup.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.member.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
