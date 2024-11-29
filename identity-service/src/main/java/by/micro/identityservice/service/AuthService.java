package by.micro.identityservice.service;

import by.micro.identityservice.dto.UserCredential;
import by.micro.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    // Создание пользователя
    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    // Создание токена
    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    // Валидация токена
    public String validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
