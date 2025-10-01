package com.sns.authservice.service;

import com.sns.authservice.dto.request.LoginRequest;
import com.sns.authservice.dto.request.RegisterRequest;
import com.sns.authservice.dto.response.AuthResponse;
import com.sns.authservice.model.RefreshToken;
import com.sns.authservice.model.Role;
import com.sns.authservice.model.User;
import com.sns.authservice.repository.RoleRepository;
import com.sns.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder encoder;


    public void register(RegisterRequest req) {
        Role userRole = roleRepo.findByName("USER")
                .orElseGet(() -> roleRepo.save(Role.builder().name("USER").build()));

        User user = User.builder()
                .username(req.getUsername())
                .password(encoder.encode(req.getPassword()))
                .roles(Set.of(userRole))
                .build();

        userRepo.save(user);
    }

    public AuthResponse login(LoginRequest req) {
        User u = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        List<String> roles = u.getRoles().stream().map(Role::getName).toList();
        String accessToken = jwtService.generateAccessToken(u.getUsername(), roles);
        long expiresAt = System.currentTimeMillis() + jwtService.getAccessTokenValidityMs();

        RefreshToken refreshToken = refreshTokenService.createToken(u.getUsername());

        return new AuthResponse(accessToken, "Bearer", expiresAt, refreshToken.getToken());
    }

    public AuthResponse refresh(String refreshToken) {
        RefreshToken rt = refreshTokenService.verifyToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        User u = userRepo.findByUsername(rt.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = u.getRoles().stream().map(Role::getName).toList();
        String newAccessToken = jwtService.generateAccessToken(u.getUsername(), roles);
        long expiresAt = System.currentTimeMillis() + jwtService.getAccessTokenValidityMs();

        return new AuthResponse(newAccessToken, "Bearer", expiresAt, rt.getToken());
    }
}