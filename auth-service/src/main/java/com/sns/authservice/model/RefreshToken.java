package com.sns.authservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true, length = 256)
    private String token;


    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private Instant expiryDate;

}
