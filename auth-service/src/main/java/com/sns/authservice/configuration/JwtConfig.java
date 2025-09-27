package com.sns.authservice.configuration;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sns.authservice.enums.AuthProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtConfig {

    @Value("${jwt.secret}")
    private static String SECRET;

    @Value("${jwt.expiration}")
    private static long expirationMs;

    public static String generateToken(UUID id, String email, String role,
                                       AuthProvider authProvider) throws Exception {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationMs);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(String.valueOf(id))
                .claim("email", email)
                .claim("authProvider", authProvider)
                .claim("role", role)
                .expirationTime(expirationDate)
                .build();
        JWSHeader header = new JWSHeader(JWSAlgorithm.ES256);

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(new MACSigner(SECRET.getBytes()));
        return signedJWT.serialize();
    }

    public static JWTClaimsSet validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET.getBytes());
            if (signedJWT.verify(verifier)) {
                Date exp = signedJWT.getJWTClaimsSet().getExpirationTime();
                if (exp.before(new Date())) {
                    throw new RuntimeException("Expired JWT token");
                }
                return signedJWT.getJWTClaimsSet();
            } else
                throw new RuntimeException("Invalid JWT token");
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token " + e.getMessage(), e);
        }
    }


}
