package com.example.doctor_patient_portal.Model;

import java.time.LocalDateTime;

import com.example.doctor_patient_portal.Websocket.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String conversationId;
    private String senderUsername;
    private Integer senderId;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;
   
}
