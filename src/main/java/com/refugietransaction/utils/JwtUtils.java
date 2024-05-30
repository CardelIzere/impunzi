	package com.refugietransaction.utils;

import com.refugietransaction.dto.auth.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;
    
    @Value("${refreshjwt.expiration}")
    private int refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getId());
        claims.put("userfullname", userDetails.getUserfullname());
        claims.put("useremail", userDetails.getUsername());
        claims.put("userphonenumber", userDetails.getUserphonenumber());
        claims.put("userrole", userDetails.getUserrole());

        if (userDetails.getSuperadmin() != null) {
            claims.put("superadminId", userDetails.getSuperadmin().getId());
            claims.put("superadmintype", userDetails.getSuperadmin().getSuperadminTypeEnum().toString());

        } else if (userDetails.getAdmin() != null) {
            claims.put("adminId", userDetails.getAdmin().getId());
            claims.put("admintype", userDetails.getAdmin().getAdminTypeEnum().toString());
            claims.put("supplier_id", userDetails.getAdmin().getSupplier().getId());
        }
        else if (userDetails.getMagasinier() != null) {
            claims.put("magasinierId", userDetails.getMagasinier().getId());
            claims.put("supplier_id", userDetails.getMagasinier().getSupplier().getId());
            claims.put("camp_id", userDetails.getMagasinier().getCamp().getId());
        }
        return createToken(claims, userDetails.getUsername(), jwtExpirationMs);
    }
    
    public String generateRefreshToken(String username) {
    	return createToken(new HashMap<>(), username, refreshExpiration);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String createToken(Map<String, Object> claims, String subject, int expiration) {
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    public Boolean isRefreshTokenValid(String token) {
    	try {
    		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    		return true;
    	} catch (Exception e){
    		return false;
    	}
    }
    
}
