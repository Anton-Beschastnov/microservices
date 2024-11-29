package by.micro.identityservice.service;

import org.springframework.stereotype.Component;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 день
                   .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                   .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
}
