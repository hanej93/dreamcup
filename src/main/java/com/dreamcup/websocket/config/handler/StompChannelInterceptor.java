package com.dreamcup.websocket.config.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dreamcup.config.jwt.provider.JwtTokenProvider;
import com.dreamcup.member.code.MemberStatusEnum;
import com.dreamcup.member.service.MemberStatusService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompChannelInterceptor implements ChannelInterceptor {

	private final MemberStatusService memberStatusService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			handleConnectEvent(accessor);
		} else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
			handleDisconnectEvent(accessor);
		}
		return message;
	}

	private void handleConnectEvent(StompHeaderAccessor accessor) {
		String jwtToken = accessor.getFirstNativeHeader("Authorization");
		log.debug("WebSocketConfig.preSend - CONNECT");
		log.debug("jwtToken = " + jwtToken);

		// todo: 개발 편의상 임시로 주석
		// if (!StringUtils.hasText(jwtToken) || !JwtTokenProvider.validateToken(jwtToken)) {
		// 	throw new AccessDeniedException("인증이 유효하지 않습니다.");
		// }

		accessor.getSessionAttributes().put("Authorization", jwtToken);
		memberStatusService.updateMemberStatus(jwtToken, MemberStatusEnum.online);
	}

	private void handleDisconnectEvent(StompHeaderAccessor accessor) {
		String jwtToken = (String) accessor.getSessionAttributes().get("Authorization");
		log.debug("WebSocketConfig.preSend - DISCONNECT");
		log.debug("jwtToken = " + jwtToken);

		if (jwtToken != null) {
			memberStatusService.updateMemberStatus(jwtToken, MemberStatusEnum.offline);
		}
	}
}
