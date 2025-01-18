package com.example.doctor_patient_portal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Userrepo;
import com.example.doctor_patient_portal.Service.TwoFactorAuthService;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

@RestController
@RequestMapping("/api/2fa")
public class TwoFactorAuthController {

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @Autowired
    private Userrepo userRepo;

    @PostMapping("/enable")
    public ResponseEntity<String> enable2FA(@RequestParam String username) {
        Users user = userRepo.findByUserIdUsername(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        String secret = twoFactorAuthService.generateSecret();
        user.setTwoFactorSecret(secret);
        user.setIsTwoFactorEnabled(true);
        userRepo.save(user);
        GoogleAuthenticatorKey credentials = new GoogleAuthenticatorKey.Builder(secret).build();
        // Use GoogleAuthenticatorQRGenerator to generate QR URL
        String qrUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL("Doctor patient portal", username, credentials);
        return ResponseEntity.ok(qrUrl);
    }
}
