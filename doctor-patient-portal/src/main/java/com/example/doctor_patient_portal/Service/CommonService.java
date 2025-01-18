package com.example.doctor_patient_portal.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Userrepo;

@Service
public class CommonService implements UserDetailsService {
    @Autowired
    JwtService jwtService;

    private AuthenticationManager authManager;
    
    @Autowired
    @Lazy
    public void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Autowired
    Userrepo repo;

    public String verify(UserDetails userDetails) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()));
        System.out.println("Sending details");
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userDetails.getUsername());
        }
        return "fail";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUserIdUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

}
