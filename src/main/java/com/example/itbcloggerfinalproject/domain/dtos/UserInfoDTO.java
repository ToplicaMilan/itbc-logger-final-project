package com.example.itbcloggerfinalproject.domain.dtos;

import com.example.itbcloggerfinalproject.domain.entities.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserInfoDTO {

    private String username;
    private String email;
    private RoleType roleType;
}
