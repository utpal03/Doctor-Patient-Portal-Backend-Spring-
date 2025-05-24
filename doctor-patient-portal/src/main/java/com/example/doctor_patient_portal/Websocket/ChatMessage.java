package com.example.doctor_patient_portal.Websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String sender; 
    private String content; 
    private MessageType type; 

    private String conversationId;
    private Integer senderId; 
    private String senderRole; 
    private Integer recipientId; 
    private String recipientRole;
 
}
