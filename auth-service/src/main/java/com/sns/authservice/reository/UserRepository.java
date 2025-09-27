package com.sns.authservice.reository;

import com.sns.authservice.enums.AuthProvider;
import com.sns.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findByProviderAndProviderId(String provider, AuthProvider authProvider);


}
