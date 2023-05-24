package com.dreamcup.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.user.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
