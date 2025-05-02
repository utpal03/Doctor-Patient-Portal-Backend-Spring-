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
import com.example.doctor_patient_portal.Model.Doctor.Doctor;
import com.example.doctor_patient_portal.Repo.DoctorAppointmentRepo;
import com.example.doctor_patient_portal.Repo.Doctorrepo;
import com.example.doctor_patient_portal.Repo.ScheduleRepo;
import com.example.doctor_patient_portal.Repo.Userrepo;

@Service
public class DoctorService {

    @Autowired
    Doctorrepo repo;

    @Autowired
    Userrepo repo1;

    @Autowired
    DoctorAppointmentRepo appointmentrepo;

    ScheduleRepo schedulerepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public Doctor adddoctor(Doctor doctor, MultipartFile profileImage) throws IOException {
        if (repo.findByUsername(doctor.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        if (profileImage != null) {
            doctor.setProfileImage(profileImage.getBytes());
            doctor.setImageName(profileImage.getOriginalFilename());
            doctor.setImageType(profileImage.getContentType());
        }
        String receivedPass = passwordEncoder.encode(doctor.getPassword());
        doctor.setPassword(receivedPass);
        Doctor savedDoc = repo.save(doctor);

        Users user = new Users();
        UserId userId = new UserId(doctor.getId(), doctor.getUsername());
        user.setPassword(receivedPass);
        user.setUserId(userId);
        user.setRole(Role.DOCTOR);
        repo1.save(user);

        return savedDoc;
    }

    public List<Doctor> showdoctor() {
        return repo.findAll();
    }

    public Doctor updateDoctor(Doctor doctor) {
        Users user = new Users();
        UserId userId = new UserId(doctor.getId(), doctor.getUsername());
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(doctor.getPassword()));
        user.setRole(Role.DOCTOR);
        repo1.save(user);

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return repo.save(doctor);
    }

    public Object viewAppointments() {
        return appointmentrepo.findAll();
    }

    public Object viewSchedule(){
        return schedulerepo.findAll();
    }

}
