package by.micro.identityservice.service;

import by.micro.identityservice.dto.UserCredential;
import by.micro.identityservice.dto.UserCredentialDto;
import by.micro.identityservice.repository.UserCredentialRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;
    // Создание пользователя
    public String saveUser(UserCredentialDto credentialDto) {
        UserCredential  credential = UserCredential.builder()
                      .username(credentialDto.getUsername())
                      .password(passwordEncoder.encode(credentialDto.getPassword()))
                      .email(credentialDto.getEmail())
                      .role(credentialDto.getRole()).build();
        repository.save(credential);
        return "user added to the system";
    }


    public String generateToken(String username) {
        Key key = getSignKey(); // Получение ключа для подписи
        Map<String, Object> claims = new HashMap<>(); // Создание пустых claims (можно добавить данные)
        return Jwts.builder()
                   .setClaims(claims) // Устанавливаем claims (в данном случае пустые)
                   .setSubject(username) // Устанавливаем subject (обычно идентификатор пользователя)
                   .setIssuer("identity-service") // Указываем издателя токена
                   .setIssuedAt(new Date(System.currentTimeMillis())) // Время выпуска токена
                   .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Время истечения токена (через 1 час)
                   .signWith(key, SignatureAlgorithm.HS256) // Подписываем токен с помощью ключа и алгоритма HS256
                   .compact(); // Генерируем компактную строку JWT
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(getSignKey())
                       .build()
                       .parseClaimsJws(token)
                       .getBody()
                       .getSubject();
        } catch (ExpiredJwtException e) {
            // Обработка случая истечения токена
            throw new IllegalArgumentException("Token is expired");
        } catch (JwtException e) {
            // Обработка других проблем с токеном
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
    private Key getSignKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
