package com.example.doctor_patient_portal.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_portal.Model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationId(String conversationId);
}
