package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.doctor_patient_portal.Model.Admin;

@Repository
public interface Adminrepo extends JpaRepository<Admin, Integer> {
    // @Query("SELECT a FROM Admin a WHERE a.username = :username")
    // Admin findByUsername(@Param("username") String username);
}
