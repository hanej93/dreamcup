package com.dreamcup.entity;

import com.dreamcup.entity.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity {

	@Id
	@Column(name = "chat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long chatId;

	@Column(name = "message", nullable = false)
	private String message;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;

	@Builder
	public Chat(String message, ChatRoom chatRoom) {
		this.message = message;
		this.chatRoom = chatRoom;
	}

}
