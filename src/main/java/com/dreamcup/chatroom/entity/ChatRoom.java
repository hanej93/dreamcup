package com.dreamcup.chatroom.entity;

import java.util.ArrayList;
import java.util.List;

import com.dreamcup.common.entity.common.BaseTimeEntity;
import com.dreamcup.member.entity.Participant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomId;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Integer maxUserCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participant_id")
	private Participant creator;

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ChatRoomParticipants> participants = new ArrayList<>();

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
	private List<Chat> chats = new ArrayList<>();

	// todo: template
	// private List<DreamCupTemplate> = new ArrayList<>();

	@Builder
	public ChatRoom(String title, String password, Integer maxUserCount, Participant creator) {
		this.title = title;
		this.password = password;
		this.maxUserCount = maxUserCount;
		this.creator = creator;
	}

	public void addParticipant(Participant participant) {
		ChatRoomParticipants chatRoomParticipants = new ChatRoomParticipants(this, participant);
		participants.add(chatRoomParticipants);
	}

	public void removeParticipant(Participant participant) {
		ChatRoomParticipants chatRoomParticipants = new ChatRoomParticipants(this, participant);
		participants.remove(chatRoomParticipants);
		chatRoomParticipants.clearChatRoomAndParticipant();
	}

	public ChatRoom(String title) {
		this.title = title;
	}

	public void update(String title) {
		this.title = title;
	}

}
