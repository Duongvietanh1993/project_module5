package com.cinema.security.jwt;

import com.cinema.security.user_principle.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {
    @Value("${expired}")
    private Long EXPIRED;
    @Value("${secret_key}")
    private String SECRET_KEY;
    private final Logger logger = LoggerFactory.getLogger(JWTProvider.class);

    public String generateToken(UserPrinciple userPrinciple) {
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRED))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return  true;
        } catch (ExpiredJwtException e) {
            logger.error("Exprired Token {}", e.getMessage());
        }catch (MalformedJwtException m){
            logger.error("Invalid format {}", m.getMessage());
        }catch (UnsupportedJwtException u){
            logger.error("Unsupported Token {}", u.getMessage());
        }catch (SignatureException s){
            logger.error("Invalid Signature Token {}", s.getMessage());
        }
        return false;
    }
    public String getUserNameToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
