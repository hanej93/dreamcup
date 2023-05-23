package com.dreamcup.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cascade;

import com.dreamcup.domain.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 100, nullable = false)
	private String title;

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Chat> chats = new ArrayList<>();

	@Builder
	public ChatRoom(String title) {
		this.title = title;
	}

	public void update(String title) {
		this.title = title;
	}

}
