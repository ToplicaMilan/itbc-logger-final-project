package com.example.itbcloggerfinalproject.domain.mappers;

import com.example.itbcloggerfinalproject.domain.RoleType;
import com.example.itbcloggerfinalproject.domain.UserEntity;
import com.example.itbcloggerfinalproject.domain.dtos.SignInDTO;
import com.example.itbcloggerfinalproject.domain.dtos.UserCreationDTO;
import lombok.AccessLevel;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public abstract class UserMapper {

    @Autowired
    @Setter(AccessLevel.PACKAGE)
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract UserEntity userCreationDtoToEntity(UserCreationDTO dto);

    @Mapping(target = "id", ignore = true)
//    public abstract UserEntity signInDtoToEntity(SignInDTO dto);
    public UserEntity signInDtoToEntity(SignInDTO dto){
        return UserEntity.builder().username(dto.getUsername()).password(passwordEncoder.encode(dto.getPassword()))
                .role(RoleType.USER).build();
    }
}
