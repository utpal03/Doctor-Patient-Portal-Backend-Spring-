package com.example.doctor_patient_portal.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Token;
import com.example.doctor_patient_portal.Repo.Tokenrepo;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final Tokenrepo tokenrepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String token = authHeader.substring(7);
        var storedToken = tokenrepo.findByToken(token)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenrepo.save(storedToken);
            SecurityContextHolder.clearContext();
        }
        var expiredToken = tokenrepo.findAll();
        deletetoken(expiredToken);
    }

    public void deletetoken(List<Token> token){
        for(Token t: token){
            if(t.isExpired() || t.isRevoked()){
                tokenrepo.delete(t);
            }
        }
    }
}