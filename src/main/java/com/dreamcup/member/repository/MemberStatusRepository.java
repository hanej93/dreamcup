package com.dreamcup.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.member.entity.Member;
import com.dreamcup.member.entity.MemberStatus;

public interface MemberStatusRepository extends JpaRepository<MemberStatus, Member> {
}
