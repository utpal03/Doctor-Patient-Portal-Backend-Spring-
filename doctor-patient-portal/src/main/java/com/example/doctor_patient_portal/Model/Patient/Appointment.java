package com.example.doctor_patient_portal.Model.Patient;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date date;
    private Time time;
    private String doctorName;
    private String specialization;
    private String location;
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
