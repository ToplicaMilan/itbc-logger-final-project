package com.example.itbcloggerfinalproject.domain.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserCreationDTO {

    private String username;
    private String email;
    private String password;
}
