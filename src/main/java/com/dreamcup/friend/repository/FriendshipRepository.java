package com.dreamcup.friend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dreamcup.friend.entity.Friendship;
import com.dreamcup.friend.repository.custom.FriendshipRepositoryCustom;
import com.dreamcup.member.entity.Member;

public interface FriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepositoryCustom {

	Optional<Friendship> findByMemberIdAndFriendId(Long memberId, Long friendId);

	@Query("SELECT f FROM Friendship f JOIN FETCH f.friend WHERE f.member = :member")
	List<Friendship> findWithFriendByMember(@Param("member") Member member);
}
