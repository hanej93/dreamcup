package com.dreamcup.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebSocketEventListener {

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		log.debug("WebSocketEventListener.handleWebSocketConnectListener");
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		log.debug("simpSessionId = " + headerAccessor.getHeader("simpSessionId"));
		log.debug("Authorization = " + headerAccessor.getHeader("Authorization"));
		log.debug("Custom-Header = " + headerAccessor.getHeader("Custom-Header"));
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		log.debug("WebSocketEventListener.handleWebSocketDisconnectListener");
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		log.debug("simpSessionId = " + headerAccessor.getHeader("simpSessionId"));
		log.debug("Authorization = " + headerAccessor.getHeader("Authorization"));
		log.debug("Custom-Header = " + headerAccessor.getHeader("Custom-Header"));
	}

}
