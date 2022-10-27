package com.example.itbcloggerfinalproject.domain.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SignInDTO {

    private String username;
    private String password;
}
