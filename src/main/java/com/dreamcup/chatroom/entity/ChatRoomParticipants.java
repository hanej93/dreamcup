package com.dreamcup.chatroom.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.dreamcup.common.entity.common.BaseTimeEntity;
import com.dreamcup.member.entity.Participant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomParticipants extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "chat_room_id")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "participant_id")
	private Participant participant;

	public ChatRoomParticipants(ChatRoom chatRoom, Participant participant) {
		this.chatRoom = chatRoom;
		this.participant = participant;
	}

	public void clearChatRoomAndParticipant() {
		this.chatRoom = null;
		this.participant = null;
	}
}
