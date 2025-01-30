package com.example.doctor_patient_portal.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.doctor_patient_portal.Service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh token has expired");
        }
        String username = jwtService.extractUserName(refreshToken);
        String newAccessToken = jwtService.generateToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);

        return ResponseEntity.ok(new TokenAuthResponse(newAccessToken, newRefreshToken));
    }
}
