package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_portal.Model.Doctor.DoctorAppointment;

public interface DoctorAppointmentRepo extends JpaRepository<DoctorAppointment, Integer> {

}
