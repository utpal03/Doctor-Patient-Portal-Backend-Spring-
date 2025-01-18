package com.example.doctor_patient_portal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Doctor;
import com.example.doctor_patient_portal.Model.UserId;
import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Doctorrepo;
import com.example.doctor_patient_portal.Repo.Userrepo;

@Service
public class DoctorService {

    @Autowired
    Doctorrepo repo;

    @Autowired
    Userrepo repo1;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public Doctor adddoctor(Doctor doctor) {
        Users user = new Users();
        UserId userId = new UserId(doctor.getId(), doctor.getUsername());
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(doctor.getPassword()));
        user.setRole("Doctor");
        repo1.save(user);

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return repo.save(doctor);
    }

    public List<Doctor> showdoctor() {
        return repo.findAll();
    }

    public Doctor updateDoctor(Doctor doctor) {
        Users user = new Users();
        UserId userId = new UserId(doctor.getId(), doctor.getUsername());
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(doctor.getPassword()));
        user.setRole("Doctor");
        repo1.save(user);

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return repo.save(doctor);
    }

}
