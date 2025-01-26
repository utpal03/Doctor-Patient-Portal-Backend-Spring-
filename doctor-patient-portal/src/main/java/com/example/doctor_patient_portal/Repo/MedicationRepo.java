package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.doctor_patient_portal.Model.Patient.Medication;

public interface MedicationRepo extends JpaRepository<Medication, Integer> {
}