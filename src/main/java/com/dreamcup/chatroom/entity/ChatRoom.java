package com.dreamcup.chatroom.entity;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import java.util.ArrayList;
import java.util.List;

import com.dreamcup.common.entity.common.BaseTimeEntity;
import com.dreamcup.member.entity.Participant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String title;

	@Column
	private String privateCode;

	@Column(nullable = false)
	private Integer maxUserCount;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "creator")
	private Participant creator;

	@OneToMany(mappedBy = "chatRoom", cascade = ALL, orphanRemoval = true)
	private List<ChatRoomParticipants> participants = new ArrayList<>();

	@OneToMany(mappedBy = "chatRoom", cascade = ALL)
	private List<Chat> chats = new ArrayList<>();

	// todo: template
	// private List<DreamCupTemplate> = new ArrayList<>();

	@Builder
	public ChatRoom(String title, String privateCode, Integer maxUserCount, Participant creator) {
		this.title = title;
		this.privateCode = privateCode;
		this.maxUserCount = maxUserCount;
		this.creator = creator;
	}

	public void addChat(Chat chat) {
		chats.add(chat);
		chat.setChatRoom(this);
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

	public boolean isOverMaxUser() {
		return this.getParticipants().size() >= this.getMaxUserCount();
	}

	public ChatRoom(String title) {
		this.title = title;
	}

	public void update(String title) {
		this.title = title;
	}

}
