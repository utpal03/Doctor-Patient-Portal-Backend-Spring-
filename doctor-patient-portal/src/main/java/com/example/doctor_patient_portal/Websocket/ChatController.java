package com.example.doctor_patient_portal.Websocket;

import com.example.doctor_patient_portal.Model.Message;
import com.example.doctor_patient_portal.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
public class ChatController {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate; // Used to send messages to specific WebSocket destinations.   
                                             
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor,
            Principal principal) {

        System.out.println("Received message: " + chatMessage.getContent() +
                " from " + chatMessage.getSender() +
                " for conversation: " + chatMessage.getConversationId());

        if (principal != null && chatMessage.getSender() == null) {
            chatMessage.setSender(principal.getName());
        }

        if (chatMessage.getConversationId() == null || chatMessage.getConversationId().isEmpty()) {
            System.err.println("Message received without conversationId: " + chatMessage);
            return;
        }
        chatService.saveMessage(chatMessage);
        messagingTemplate.convertAndSend("/topic/chat/" + chatMessage.getConversationId(), chatMessage);
    }

    @MessageMapping("/chat.addUser")
    public void addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor,
            Principal principal) {

        String username = principal != null ? principal.getName() : chatMessage.getSender();
        if (username != null) {
            chatMessage.setSender(username);
            Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", username);
            Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", chatMessage.getSenderId());
            Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("conversationId",
                    chatMessage.getConversationId());

            chatService.saveMessage(chatMessage);

            String conversationId = chatMessage.getConversationId();
            if (conversationId != null && !conversationId.isEmpty()) {
                messagingTemplate.convertAndSend("/topic/chat/" + conversationId, chatMessage);
                System.out.println("User " + username + " joined conversation: " + conversationId);
            }
        }
    }

    @MessageMapping("/chat.history")
    public void getChatHistory(@Payload ChatMessage requestMessage, Principal principal) {

        if (requestMessage.getConversationId() != null && !requestMessage.getConversationId().isEmpty()) {
            List<Message> history = chatService.getConversationHistory(requestMessage.getConversationId());
            messagingTemplate.convertAndSendToUser(
                    principal.getName(),
                    "/queue/history",
                    history);
            System.out.println("Sent history for conversation " + requestMessage.getConversationId() + " to "
                    + principal.getName());
        }
    }
}