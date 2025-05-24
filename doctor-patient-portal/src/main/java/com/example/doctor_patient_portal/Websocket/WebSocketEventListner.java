package com.example.doctor_patient_portal.Websocket;

import com.example.doctor_patient_portal.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
public class WebSocketEventListner {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ChatService chatService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = Objects.requireNonNull(headerAccessor.getSessionId());
        System.out.println("Received a new web socket connection: " + sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username");

        Object storedUserId = headerAccessor.getSessionAttributes().get("userId");
        Integer userId = null;

        if (storedUserId instanceof Integer) {
            userId = (Integer) storedUserId;
        } else if (storedUserId instanceof String) {
            try {
                userId = Integer.parseInt((String) storedUserId);
            } catch (NumberFormatException e) {
                System.err.println(
                        "Error: userId in session is a String that cannot be parsed to Integer: " + storedUserId);

            }
        }
        String conversationId = (String) headerAccessor.getSessionAttributes().get("conversationId");

        if (username != null && userId != null && conversationId != null) {
            System.out.println(
                    "User Disconnected: " + username + " (ID: " + userId + ") from conversation: " + conversationId);

            ChatMessage chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .senderId(userId)
                    .conversationId(conversationId)
                    .content(username + " has left the chat.")
                    .build();

            chatService.saveMessage(chatMessage);
            messagingTemplate.convertAndSend("/topic/chat/" + conversationId, chatMessage);
        } else {
            System.out.println("Disconnected session (missing user info or userId parsing failed): "
                    + Objects.requireNonNull(headerAccessor.getSessionId()));
        }
    }
}