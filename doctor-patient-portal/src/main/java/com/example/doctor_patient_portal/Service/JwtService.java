package com.example.doctor_patient_portal.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private String secretkey = "";

    private long jwtExpiration = 2 * 60 * 1000;
    private long refreshTokenExpritation = 60 * 60 * 1000 * 168; // 7days

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keygen.generateKey();
        secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return buildToken(claims, username, jwtExpiration);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return buildToken(claims, username, refreshTokenExpritation);
    }

    private SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(key);
    }

    public String buildToken(Map<String, Object> claims, String username, long expiration) {
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getKey())
                .compact();

    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // extract claims even if expired
        }
    }

    public boolean validateToken(String token, UserDetails userdetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userdetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
