package com.lastdance.beeper.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastdance.beeper.service.impl.UserServiceImpl;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignalingSocketHandler extends TextWebSocketHandler {

    // 각 방에 대한 세션을 저장할 맵
    private final Map<String, Map<String, WebSocketSession>> rooms = new HashMap<>();

    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> data = new ObjectMapper().readValue(payload, HashMap.class);

        LOGGER.info("[handleTextMessage] 서버 요청 roomId: {}", data.get("roomId"));
        String roomId = (String) data.get("roomId");


        if (!rooms.containsKey(roomId)) {
            rooms.put(roomId, new HashMap<>());
        }

        rooms.get(roomId).put(session.getId(), session);

        // 메시지 타입에 따라 처리 (offer, answer, candidate)
        for (WebSocketSession s : rooms.get(roomId).values()) {
            if (!s.getId().equals(session.getId())) {
                s.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 세션이 열리면 연결 정보를 저장하는 코드
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        rooms.values().forEach(room -> room.remove(session.getId()));

        LOGGER.info("[handleTextMessage] 화상통화 종료");
    }
}
