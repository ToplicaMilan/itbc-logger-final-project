package com.example.itbcloggerfinalproject.controllers;

import com.example.itbcloggerfinalproject.domain.dtos.LogDTO;
import com.example.itbcloggerfinalproject.domain.dtos.SignInDTO;
import com.example.itbcloggerfinalproject.domain.dtos.UserCreationDTO;
import com.example.itbcloggerfinalproject.security.TokenDTO;
import com.example.itbcloggerfinalproject.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@AllArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping(value = "/signup")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TokenDTO> signUp(@RequestBody UserCreationDTO dto) {
        String jwt = service.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTokenDTO(jwt));
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<TokenDTO> signIn(@RequestBody SignInDTO dto) {
        String jwt = service.provideToken(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(createTokenDTO(jwt));
    }

    @PostMapping(value = "create_log")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> createLog(@RequestBody LogDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        service.createLog(dto, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto.getUserLog());
    }

//    @PostMapping(value = "create_log")
//    public ResponseEntity<String> createLog(@RequestBody LogDTO dto, Authentication authentication) {
//        authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        UserEntity user = service.getUserByUsername(currentPrincipalName);
//        user.getUserLogs().add(service.createLog(dto));
//        return ResponseEntity.ok(dto.getUserLog());
//    }

    private TokenDTO createTokenDTO(String jwt) {
        return TokenDTO.builder().accessToken(jwt).build();
    }

}
