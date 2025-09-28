package com.sns.authservice.mapper;


import com.sns.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(User user);

    @Mapping(target = "role", ignore = true)
    User updateUser(User user, @MappingTarget User target);

}
