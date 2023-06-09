package com.dreamcup.member.entity;

import static jakarta.persistence.GenerationType.*;

import com.dreamcup.common.entity.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Participant extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickName;

	private String nameTag;

	public abstract boolean isAnonymous();

	protected Participant(String nickName, String nameTag) {
		this.nickName = nickName;
		this.nameTag = nameTag;
	}
}
