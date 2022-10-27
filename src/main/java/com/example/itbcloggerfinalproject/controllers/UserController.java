package com.example.itbcloggerfinalproject.controllers;

import com.example.itbcloggerfinalproject.domain.dtos.SignInDTO;
import com.example.itbcloggerfinalproject.domain.dtos.UserCreationDTO;
import com.example.itbcloggerfinalproject.domain.mappers.UserMapper;
import com.example.itbcloggerfinalproject.security.TokenDTO;
import com.example.itbcloggerfinalproject.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping(value = "signup")
    public ResponseEntity<TokenDTO> signUp(UserCreationDTO dto) {
        String jwt = service.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTokenDTO(jwt));
    }

    public ResponseEntity<TokenDTO> signIn(SignInDTO dto) {
        String jwt = service.provideToken(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }

}
