package com.sns.authservice.mapper;

import com.sns.authservice.dto.AuthResponse;
import com.sns.authservice.dto.RegisterRequest;
import com.sns.authservice.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequest registerRequest);

    AuthResponse toAuthResponse(User user, @Context String token);

    @AfterMapping
    default void afterMapping(User user, @MappingTarget AuthResponse.AuthResponseBuilder response, @Context String token) {
        response.token(token);
    }
}
