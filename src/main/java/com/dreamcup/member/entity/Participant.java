package com.dreamcup.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.dreamcup.chatroom.entity.ChatRoom;
import com.dreamcup.chatroom.entity.ChatRoomParticipants;
import com.dreamcup.common.entity.common.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Participant extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickName;

	private String nameTag;

	@OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ChatRoomParticipants> chatRoomParticipants = new ArrayList<>();

	public abstract boolean isAnonymous();

	protected Participant(String nickName, String nameTag) {
		this.nickName = nickName;
		this.nameTag = nameTag;
	}
}
