package com.sns.authservice.dto;

import lombok.Data;

@Data
public class OAuthLoginRequest {
    private String provider;   // GOOGLE, APPLE
    private String idToken;    // idToken từ provider
}
