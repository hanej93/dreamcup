package com.dreamcup.friend.entity;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.dreamcup.chatroom.code.MessageType;
import com.dreamcup.common.entity.common.BaseTimeEntity;
import com.dreamcup.friend.code.FriendChatMessageType;
import com.dreamcup.friend.code.ReadStatus;
import com.dreamcup.member.entity.Participant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendChat extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String message;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "sender_id")
	private Participant sender;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "receiver_id")
	private Participant receiver;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FriendChatMessageType messageType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ReadStatus readStatus;

}
