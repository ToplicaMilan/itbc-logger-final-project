package com.example.itbcloggerfinalproject.controllers;

import com.example.itbcloggerfinalproject.domain.dtos.UserInfoDTO;
import com.example.itbcloggerfinalproject.domain.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get_users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserInfoDTO>> getAllUser() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body((adminService.getAllUsers()));
    }

    @PatchMapping("/reset-password/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changePassword(@RequestBody String password, @PathVariable(name = "id") Long id) {
        adminService.changePassword(id, password);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed!");
    }
}
