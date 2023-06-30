package com.dreamcup.friend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamcup.friend.entity.Friendship;
import com.dreamcup.friend.repository.custom.FriendshipRepositoryCustom;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepositoryCustom {

	Optional<Friendship> findByMemberIdAndFriendId(Long memberId, Long friendId);

}
