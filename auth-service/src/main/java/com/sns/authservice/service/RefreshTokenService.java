package com.sns.authservice.service;


import com.sns.authservice.model.RefreshToken;
import com.sns.authservice.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repo;
    private final long refreshTokenValidityMs;

    public RefreshTokenService(RefreshTokenRepository repo,
                               @Value("${jwt.refresh-token-validity-ms}") long refreshTokenValidityMs) {
        this.repo = repo;
        this.refreshTokenValidityMs = refreshTokenValidityMs;
    }

    public RefreshToken createToken(String username) {
        repo.deleteByUsername(username);
        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .username(username)
                .expiryDate(Instant.now().plusMillis(refreshTokenValidityMs))
                .build();
        return repo.save(token);
    }

    public Optional<RefreshToken> verifyToken(String token) {
        return repo.findByToken(token)
                .filter(rt -> rt.getExpiryDate().isAfter(Instant.now()));
    }
}