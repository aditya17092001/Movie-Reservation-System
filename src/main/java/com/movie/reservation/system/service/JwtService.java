package com.movie.reservation.system.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.movie.reservation.system.model.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service 
public class JwtService {
	
    @Value("${jwt.secret.key}")
	public String KEY;

	public String  generateToken(UUID uuid) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", uuid);
		
		return Jwts
				.builder()
				.claims() 
				.add(claims)
				.subject(new String(uuid+""))
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
				.and()
				.signWith(getKey())
				.compact();
				
	}
	
	private Key getKey() {
		byte keyByte[] = Decoders.BASE64.decode(KEY);
		return Keys.hmacShaKeyFor(keyByte);
	}

	public String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("id", String.class);
    }

    public UUID extractId(String token) {
        Claims claims = extractAllClaims(token);
        String subject = claims.getSubject();
        return UUID.fromString(subject);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);
            String user_id = extractUserId(token);
            return (user_id.equals(userDetails.getUsername()) && !isTokenExpired(claims));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
    
    public Claims extractAllClaimsFromContext() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getCredentials() == null) {
            return null;
        }

        String jwtToken = auth.getCredentials().toString();
        return extractAllClaims(jwtToken);
    }
}

