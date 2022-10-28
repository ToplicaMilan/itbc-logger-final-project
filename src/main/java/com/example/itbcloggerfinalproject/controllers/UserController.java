package com.example.itbcloggerfinalproject.controllers;

import com.example.itbcloggerfinalproject.domain.dtos.LogDTO;
import com.example.itbcloggerfinalproject.domain.dtos.SignInDTO;
import com.example.itbcloggerfinalproject.domain.dtos.UserCreationDTO;
import com.example.itbcloggerfinalproject.domain.dtos.UserInfoDTO;
import com.example.itbcloggerfinalproject.domain.entities.LogType;
import com.example.itbcloggerfinalproject.security.TokenDTO;
import com.example.itbcloggerfinalproject.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<TokenDTO> signUp(@RequestBody UserCreationDTO dto) {
        String jwt = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTokenDTO(jwt));
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<TokenDTO> signIn(@RequestBody SignInDTO dto) {
        String jwt = userService.provideToken(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    @PostMapping(value = "create_log")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> createLog(@RequestBody LogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.createLog(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getUserLog());
    }

    @PostMapping(value = "create_log")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<LogDTO>> getLogsByType(@RequestBody LogType logType) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body((userService.getLogsByType(logType)));
    }


    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }

}
