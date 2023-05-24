package com.dreamcup.chatroom.entity;

import java.util.ArrayList;
import java.util.List;

import com.dreamcup.common.entity.Chat;
import com.dreamcup.common.entity.common.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chat_room_id")
	private Long chatRoomId;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
	private List<Chat> chats = new ArrayList<>();

	@Builder
	public ChatRoom(String title) {
		this.title = title;
	}

	public void update(String title) {
		this.title = title;
	}

}
