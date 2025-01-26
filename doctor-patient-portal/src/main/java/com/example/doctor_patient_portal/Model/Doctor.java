package com.example.doctor_patient_portal.Model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Component
@Entity
@Data
public class Doctor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String department;
    private String phoneNumber;
    private String licenseNumber;
    private Double consultationFees;
    private List<String> availableDays;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private int experience;
    private String username;
    private String password;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setRole(String roleString) {
        this.role = Role.fromString(roleString.toUpperCase());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}