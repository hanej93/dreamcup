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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatRoomId;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(nullable = false)
	private String roomPassword;

	@Column(nullable = false)
	private Integer maxUserCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "participant_id")
	private Participant participant;

	// todo: template
	// private List<DreamCupTemplate> = new ArrayList<>();

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
	private List<Chat> chats = new ArrayList<>();

	public ChatRoom(String title) {
		this.title = title;
	}

	public void update(String title) {
		this.title = title;
	}

	@Builder
	public ChatRoom(String title, String roomPassword, Integer maxUserCount, Participant participant) {
		this.title = title;
		this.roomPassword = roomPassword;
		this.maxUserCount = maxUserCount;
		this.participant = participant;
	}
}
