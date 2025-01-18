package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.doctor_patient_portal.Model.UserId;
import com.example.doctor_patient_portal.Model.Users;

public interface Userrepo extends JpaRepository<Users, UserId> {
    @Query("SELECT u FROM Users u WHERE u.userId.username = :username")
    Users findByUserIdUsername(String username);
}
