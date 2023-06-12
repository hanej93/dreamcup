package com.dreamcup.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.member.entity.Friendship;
import com.dreamcup.member.repository.custom.FriendshipRepositoryCustom;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepositoryCustom {

	Optional<Friendship> findByMemberIdAndFriendId(Long memberId, Long friendId);

}
