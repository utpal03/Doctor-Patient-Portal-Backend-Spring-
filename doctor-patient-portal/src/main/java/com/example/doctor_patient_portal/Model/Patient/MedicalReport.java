package com.example.doctor_patient_portal.Model.Patient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "medical_reports")
@Data
@Entity
public class MedicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private byte[] MedicalReport;
    private byte[] LabReport;
    private String date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}