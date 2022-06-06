package com.example.diningreview.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private final String jwtSecret;

    private final int jwtExpirationTimeInHrs;

    public JwtTokenUtil(@Value("${app.jwtSecret}") String jwtSecret,
                        @Value("${app.jwtExpirationTimeInHrs}") int jwtExpirationTimeInMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationTimeInHrs = jwtExpirationTimeInMs;
    }

    public String generateToken(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        LocalDateTime date = LocalDateTime.now();

        List<String> authorities = principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        LocalDateTime datePlus = date.plusHours(jwtExpirationTimeInHrs);

        Date expiryDate = Date.from(datePlus.atZone(ZoneId.systemDefault()).toInstant());

        Key hmacKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .addClaims(Map.of("roles", authorities))
                .setExpiration(expiryDate)
                .signWith(hmacKey)
                .compact();
    }

    public String getUsername(String token) {
        Key hmacKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName());

          Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey)
                .build()
                .parseClaimsJws(token);

          return claims.getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Key hmacKey = new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8),
                    SignatureAlgorithm.HS256.getJcaName());

            Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
