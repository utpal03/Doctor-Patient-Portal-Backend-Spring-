package com.example.doctor_patient_portal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Admin;
import com.example.doctor_patient_portal.Model.UserId;
import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Adminrepo;
import com.example.doctor_patient_portal.Repo.Userrepo;

@Service
public class AdminService{

    @Autowired
    Adminrepo repo;

    @Autowired
    Userrepo repo1;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public List<Admin> showadmin() {
        return repo.findAll();
    }

    public Admin register(Admin admin) {
        UserId userId = new UserId(admin.getId(), admin.getUsername());
        Users user = new Users();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setRole("Admin");
        repo1.save(user);

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return repo.save(admin);
    }

}
