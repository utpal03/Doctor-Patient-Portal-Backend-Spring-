package com.example.doctor_patient_portal.Auth;

import com.example.doctor_patient_portal.Model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private int id;
    private String accessToken;
    private String refreshToken;
    private Role roles;
}
