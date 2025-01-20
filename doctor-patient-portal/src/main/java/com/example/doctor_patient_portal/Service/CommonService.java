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

import com.example.doctor_patient_portal.Model.Token;
import com.example.doctor_patient_portal.Model.Tokentype;
import com.example.doctor_patient_portal.Model.Users;
import com.example.doctor_patient_portal.Repo.Tokenrepo;
import com.example.doctor_patient_portal.Repo.Userrepo;

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

    public String verify(UserDetails userDetails) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(userDetails.getUsername());
            Users user = userrepo.findByUserIdUsername(userDetails.getUsername());
            if (user == null) {
                throw new UsernameNotFoundException("User not found in Users table.");
            }
            revokeAllUserTokens(user);
            saveUserToken(user, token);
            return token;
        }
        return "fail";
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

}
