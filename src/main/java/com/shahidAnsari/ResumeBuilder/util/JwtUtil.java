package com.shahidAnsari.ResumeBuilder.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public String generateToken(String userId){
        Date now = new Date();
        Date expirydate = new Date(now.getTime()+jwtExpiration);

        return Jwts.builder().setSubject(userId).setIssuedAt(now).setExpiration(expirydate)
                .signWith(getSigningkey()).compact();
    }

    private Key getSigningkey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningkey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(getSigningkey()).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public boolean isTokenExpired(String token){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningkey())
                    .parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            return true;
        }
    }
}
