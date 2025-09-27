package com.sns.authservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private Long id;
    private String email;
    private String name;
    private String avatarUrl;
    private String token;   // JWT
}