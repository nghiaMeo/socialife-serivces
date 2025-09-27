package com.sns.authservice.model;


import com.sns.authservice.enums.AuthProvider;
import com.sns.authservice.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String city;
    private LocalDate birthday;


}
