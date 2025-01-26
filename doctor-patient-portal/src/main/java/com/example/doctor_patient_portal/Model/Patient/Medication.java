package com.example.doctor_patient_portal.Model.Patient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String dosage;
    private String frequency;
    private String timeLeft;
    private String prescribedBy;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
