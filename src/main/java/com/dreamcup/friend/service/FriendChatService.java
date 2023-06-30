package com.dreamcup.friend.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dreamcup.chatroom.code.MessageType;
import com.dreamcup.friend.config.FriendExchangeConfig;
import com.dreamcup.friend.dto.request.FriendSendChatRequestDto;
import com.dreamcup.friend.entity.FriendChat;
import com.dreamcup.friend.repository.FriendChatRepository;
import com.dreamcup.member.entity.Participant;
import com.dreamcup.member.exception.UserNotFoundException;
import com.dreamcup.member.repository.ParticipantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendChatService {

	private final FriendChatRepository friendChatRepository;
	private final ParticipantRepository participantRepository;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	public void sendChatMessage(FriendSendChatRequestDto requestDto) {
		Participant sender = participantRepository.findById(requestDto.getSenderId())
			.orElseThrow(UserNotFoundException::new);

		Participant receiver = participantRepository.findById(requestDto.getReceiverId())
			.orElseThrow(UserNotFoundException::new);

		FriendChat chat = FriendChat.builder()
			.message(requestDto.getMessage())
			.sender(sender)
			.receiver(receiver)
			.messageType(MessageType.MEMBER)
			.build();

		// 메시지 저장
		friendChatRepository.save(chat);

		String routingKeySeparator = "-";
		String senderRoutingKey = "message." + requestDto.getSenderId() + routingKeySeparator + requestDto.getReceiverId();
		String receiverRoutingKey = "message." + requestDto.getReceiverId() + routingKeySeparator + requestDto.getSenderId();

		// 메시지 소켓 전송
		rabbitTemplate.convertAndSend(FriendExchangeConfig.FRIEND_EXCHANGE_NAME, senderRoutingKey, chat.getMessage());
		rabbitTemplate.convertAndSend(FriendExchangeConfig.FRIEND_EXCHANGE_NAME, receiverRoutingKey, chat.getMessage());
	}

}
