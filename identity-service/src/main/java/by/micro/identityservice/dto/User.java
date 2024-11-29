package by.micro.identityservice.dto;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}