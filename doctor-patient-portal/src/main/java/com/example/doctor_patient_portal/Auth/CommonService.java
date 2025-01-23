package com.example.doctor_patient_portal.Auth;

import java.io.IOException;
import com.example.doctor_patient_portal.Model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.doctor_patient_portal.Model.Token;
import com.example.doctor_patient_portal.Model.Tokentype;
import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Tokenrepo;
import com.example.doctor_patient_portal.Repo.Userrepo;
import com.example.doctor_patient_portal.Service.JwtService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CommonService implements UserDetailsService {
    @Autowired
    JwtService jwtService;

    @Autowired
    Tokenrepo tokenrepo;

    @Autowired
    Userrepo userrepo;

    private AuthenticationManager authManager;

    @Autowired
    @Lazy
    public void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Autowired
    Userrepo repo;

    public AuthResponse verify(AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getUsername());
            String refreshToken = jwtService.generateRefreshToken(authRequest.getUsername());
            Users user = userrepo.findByUserIdUsername(authRequest.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("User not found in Users table.");
            }
            Role role = user.getRole();
            revokeAllUserTokens(user);
            saveUserToken(user, refreshToken);
            return new AuthResponse(token, refreshToken, role);
        }
        throw new BadCredentialsException("Authentication failed");
    }

    private void saveUserToken(Users user, String jwtToken) {
        var token = Token.builder()
                .users(user)
                .token(jwtToken)
                .tokentype(Tokentype.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenrepo.save(token);
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenrepo.findAllValidTokenByUser(user.getUserId().getId(), user.getUsername());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenrepo.saveAll(validUserTokens);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUserIdUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public void refreshToken(HttpServletRequest req, HttpServletResponse res)
            throws StreamWriteException, DatabindException, IOException {
        String authHeader = req.getHeader("Authorization");
        String refreshToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUserName(refreshToken);

        if (username != null) {
            UserDetails userdetails = loadUserByUsername(username);
            if (jwtService.validateToken(refreshToken, userdetails)) {

                Users user = repo.findByUserIdUsername(userdetails.getUsername());
                var accesstoken = jwtService.generateToken(username);

                revokeAllUserTokens(user);
                saveUserToken(user, refreshToken);

                var authResponse = AuthResponse.builder()
                        .accessToken(accesstoken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(res.getOutputStream(), authResponse);
            }
        }
    }

}
