package com.example.doctor_patient_portal.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.doctor_patient_portal.Repo.Tokenrepo;
import com.example.doctor_patient_portal.Service.JwtService;
import com.example.doctor_patient_portal.Service.LogoutService;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private Tokenrepo tokenRepo;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        System.out.println(refreshTokenRequest);
        String refreshToken = refreshTokenRequest.getRefreshToken();
        var storedToken = tokenRepo.findByToken(refreshToken)
                .orElse(null);

        if (storedToken == null || storedToken.isExpired() || storedToken.isRevoked()) {
            return ResponseEntity.status(401).body("Invalid or expired refresh token");
        }

        String username = jwtService.extractUserName(refreshToken);
        String newAccessToken = jwtService.generateToken(username);

        System.out.println("new access token genereated");
        return ResponseEntity.ok(new TokenAuthResponse(newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return ResponseEntity.ok("Logout Successfully");
    }
}
