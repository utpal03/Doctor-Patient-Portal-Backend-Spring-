package com.example.doctor_patient_portal.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.doctor_patient_portal.Model.Role;
import com.example.doctor_patient_portal.Model.UserId;
import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Model.Patient.Patient;
import com.example.doctor_patient_portal.Repo.AppointmentRepo;
import com.example.doctor_patient_portal.Repo.MedicalReportRepo;
import com.example.doctor_patient_portal.Repo.MedicationRepo;
import com.example.doctor_patient_portal.Repo.Patientrepo;
import com.example.doctor_patient_portal.Repo.Userrepo;

@Service
public class PatientService {
    @Autowired
    Patientrepo repo;

    @Autowired
    Userrepo repo1;

    @Autowired
    MedicalReportRepo medicalRepo;

    @Autowired
    AppointmentRepo appointmentRepo;

    @Autowired
    MedicationRepo medicationRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public List<Patient> showpatient() {
        return repo.findAll();
    }

    public Patient addpatient(Patient patient, MultipartFile profileimage) throws IOException {
        if (repo.findByUsername(patient.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.findByEmail(patient.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }
        if (profileimage != null) {

            patient.setProfileImage(profileimage.getBytes());
            patient.setImageName(profileimage.getOriginalFilename());
            patient.setImageType(profileimage.getContentType());

        }

        String receviedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(receviedPassword);
        Patient savedPatient = repo.save(patient);

        UserId userId = new UserId(savedPatient.getId(), savedPatient.getUsername());
        Users user = new Users();
        user.setUserId(userId);
        user.setPassword(receviedPassword);
        user.setRole(Role.PATIENT);
        repo1.save(user);
        return savedPatient;
    }

    public Object viewAppointments() {
        return appointmentRepo.findAll();
    }

    public Object viewMedications() {
        return medicationRepo.findAll();
    }

    public Object viewReports() {
        return medicalRepo.findAll();
    }
}
