package com.example.cedarxpressliveprojectjava010.configuration.jwt;

import com.example.cedarxpressliveprojectjava010.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int JwtExpirationInMs;

    @Value("${app.reset-password-expiration-ms}")
    private int JwtPasswordResetInMs;

    // generate token
    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + JwtExpirationInMs);

        String token  = Jwts.builder()
                .setSubject(email)
                .claim("Authorities", authentication.getAuthorities())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return "Bearer "  + token;
    }

    public Jws<Claims> validateToken (String token){
        Jws<Claims> claimsJWS;
        try {
        claimsJWS = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token);

        }catch (SignatureException ex){
            throw  new InvalidJwtTokenException(HttpStatus.BAD_REQUEST, "invalid jwt token");
        }
        return claimsJWS;
    }


    public String generateResetPasswordJWT(User user){
        String email = user.getEmail();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + JwtPasswordResetInMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, user.getPassword())
                .compact();
    }

    public Jws<Claims> validateResetPasswordJWT (String token, String secretKey){
        Jws<Claims> claimsJWS;
        try {
            claimsJWS = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

        }catch (SignatureException ex){
            throw  new InvalidJwtTokenException(HttpStatus.BAD_REQUEST, "Token expired!");
        }
        return claimsJWS;
    }
}
