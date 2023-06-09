package com.dreamcup.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.member.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
