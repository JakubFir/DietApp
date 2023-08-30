package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "2D4B6150645367566B58703273357638792F423F4528482B4D6251655468576D";
    private final UserRepository userRepository;

    public String generateToken(String username){
        return generateToken(new HashMap<>(),username);
    }

    public String generateToken(Map<String, Object> extraClaims, String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        extraClaims.put("Role", user.getRole());
        extraClaims.put("UserId", user.getUserId());
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }
    public boolean isTokenValid(String token, String subject) {
        final String username = extractUsername(token);
        return (username.equals(subject)) && !isTokenExpired(token);
    }
    private boolean isTokenExpired(String token) {
        Date today = Date.from(Instant.now());
        return extractAllClaims(token).getExpiration().before(today);
    }
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
