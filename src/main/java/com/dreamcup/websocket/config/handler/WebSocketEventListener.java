package com.dreamcup.websocket.config.handler;

import java.util.List;
import java.util.Map;

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
		Map<String, List<String>> headers = headerAccessor.toNativeHeaderMap();
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			log.debug("Header: " + entry.getKey() + " = " + entry.getValue());
		}
		if (headerAccessor.getSessionAttributes() != null) {
			for (String key : headerAccessor.getSessionAttributes().keySet()) {
				log.debug("WS SESSION, {}: {}", key, headerAccessor.getSessionAttributes().get(key));
			}
		}
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		log.debug("WebSocketEventListener.handleWebSocketDisconnectListener");
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		Map<String, List<String>> headers = headerAccessor.toNativeHeaderMap();
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			log.debug("Header: " + entry.getKey() + " = " + entry.getValue());
		}
		if (headerAccessor.getSessionAttributes() != null) {
			for (String key : headerAccessor.getSessionAttributes().keySet()) {
				log.debug("WS SESSION, {}: {}", key, headerAccessor.getSessionAttributes().get(key));
			}
		}
	}

}
