package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_portal.Model.Doctor.Doctor;

public interface ScheduleRepo extends JpaRepository<Doctor, Integer> {

}
