package by.micro.identityservice.controller;

import by.micro.identityservice.dto.UserCredential;
import by.micro.identityservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;

    // Создание пользователя
    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return  authService.saveUser(user);
    }
    // Создание токена
    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate()
        String token = authService.generateToken(authRequest);
        return ResponseEntity.ok(token);
    }

    // Валидация токена
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        String username = authService.validateToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok("Valid token for user: " + username);
    }
}