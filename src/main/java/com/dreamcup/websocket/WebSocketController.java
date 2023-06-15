package com.dreamcup.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dreamcup.chatroom.vo.ChatVo;
import com.dreamcup.websocket.dto.request.ChatMessageRequestDto;
import com.dreamcup.websocket.dto.request.ChatRoomJoinRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebSocketController {

	private final WebSocketService webSocketService;

	@MessageMapping("/message")
	public void sendMessage(ChatMessageRequestDto requestDto) {
		log.debug("WebSocketController.sendMessage : {}", requestDto);
		webSocketService.sendChatMessage(requestDto);
	}

	// todo : 채팅방 입장
	@MessageMapping("/chat-room/join")
	public void joinChatRoom(ChatRoomJoinRequestDto requestDto) {
		// 참여자 목록 반환
		// 시스템 메시지
	}

	// todo : 채팅방 나가기
	@MessageMapping("/chat-room/leave")
	public void leaveChatRoom() {
		// 참여자 목록 반환
		// 시스템 메시지
		// 방장이 나가거나 참여자가 없으면 채팅방 종료 -> 나머지 강제종료
	}

}
