package com.sns.authservice.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
