package com.example.doctor_patient_portal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Patient;
import com.example.doctor_patient_portal.Model.Role;
import com.example.doctor_patient_portal.Model.UserId;
import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Patientrepo;
import com.example.doctor_patient_portal.Repo.Userrepo;

@Service
public class PatientService {
    @Autowired
    Patientrepo repo;

    @Autowired
    Userrepo repo1;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public List<Patient> showpatient() {
        return repo.findAll();
    }

    public Patient addpatient(Patient patient) {
        UserId userId = new UserId(patient.getId(), patient.getUsername());
        Users user = new Users();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(patient.getPassword()));
        user.setRole(Role.PATIENT);
        repo1.save(user);
        
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setRole(Role.PATIENT);
        return repo.save(patient);
    }
}
