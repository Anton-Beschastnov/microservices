package by.micro.identityservice.service;

import by.micro.identityservice.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
//
//
///**
// * loadUserByUsername - dao аутентификация, секьюрите вызывает этот метод,
// * что проверить подлинность юзера, который пытается залогиниться
// * UserDetails содержит логин пароль и роли
// */
//@Service
//@RequiredArgsConstructor
//public class UserService implements UserDetailsService {
//    private final UserCredentialRepository userCredentialRepository;
////    private final PasswordEncoder passwordEncoder;
////
////    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
////        this.userRepository = userRepository;
////        this.passwordEncoder = passwordEncoder;
////    }
////
////    public User registerUser(String username, String password) {
////        if (userRepository.findByUsername(username).isPresent()) {
////            throw new IllegalArgumentException("Username already exists");
////        }
////        User user = new User();
////        user.setUsername(username);
////        user.setPassword(passwordEncoder.encode(password));
////        return userRepository.save(user);
////    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userCredentialRepository.findByUsername(username)
//                                       .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        Collections.singleton(user.getRole())
//                ))
//                                       .orElseThrow(() -> new UsernameNotFoundException("Invalid token: " + username));
//    }
//}
