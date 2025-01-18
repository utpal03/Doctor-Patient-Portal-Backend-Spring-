package com.example.doctor_patient_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.doctor_patient_portal.Model.Patient;

@Repository
public interface Patientrepo extends JpaRepository<Patient, Integer> {

}
