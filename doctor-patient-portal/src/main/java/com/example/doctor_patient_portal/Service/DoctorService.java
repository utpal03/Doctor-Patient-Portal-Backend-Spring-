package com.example.doctor_patient_portal.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.doctor_patient_portal.Model.Doctor;
import com.example.doctor_patient_portal.Model.Role;
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

    public Doctor adddoctor(Doctor doctor, MultipartFile profileImage) throws IOException {
        Users user = new Users();
        UserId userId = new UserId(doctor.getId(), doctor.getUsername());
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(doctor.getPassword()));
        user.setRole(Role.DOCTOR);
        repo1.save(user);

        if (profileImage != null) {
            doctor.setProfileImage(profileImage.getBytes());
            doctor.setImageName(profileImage.getOriginalFilename());
            doctor.setImageType(profileImage.getContentType());
        }

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
        user.setRole(Role.DOCTOR);
        repo1.save(user);

        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return repo.save(doctor);
    }

}
