package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_portal.Model.Patient.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    
}
