package com.example.doctor_patient_portal.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Message;
import com.example.doctor_patient_portal.Repo.MessageRepository;
import com.example.doctor_patient_portal.Websocket.ChatMessage;

@Service
public class ChatService {
    private MessageRepository messageRepository;

    public Message saveMessage(ChatMessage chatMessage) {
        Message message = new Message();
        message.setConversationId(chatMessage.getConversationId());
        message.setSenderUsername(chatMessage.getSender());
        message.setSenderId(chatMessage.getSenderId());
        message.setContent(chatMessage.getContent());
        message.setType(chatMessage.getType());
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getConversationHistory(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
    
}
