package com.example.doctor_patient_portal.Websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String Sender;
    private String content;
    private MessageType type;
}
