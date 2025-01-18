package com.example.doctor_patient_portal.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Userrepo;

public class UserService implements UserDetailsService {
    @Autowired
    Userrepo repo;

    public List<Users> showusers() {
        return repo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUserIdUsername(username);
        if (user == null) {
            System.out.println("username is null or no username found in database");
        }
        return user;
    }

}
