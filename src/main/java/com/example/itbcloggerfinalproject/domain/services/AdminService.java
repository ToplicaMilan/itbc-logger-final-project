package com.example.itbcloggerfinalproject.domain.services;

import com.example.itbcloggerfinalproject.domain.dtos.UserInfoDTO;
import com.example.itbcloggerfinalproject.domain.entities.UserEntity;
import com.example.itbcloggerfinalproject.domain.mappers.LogMapper;
import com.example.itbcloggerfinalproject.domain.mappers.UserMapper;
import com.example.itbcloggerfinalproject.exceptions.UserNotFoundException;
import com.example.itbcloggerfinalproject.repositories.LogRepository;
import com.example.itbcloggerfinalproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    public final UserMapper userMapper;
    public final LogMapper logMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserInfoDTO> getAllUsers() {
        List<UserInfoDTO> usersInfo = new ArrayList<>();
        for (UserEntity user : userRepository.findAll()) {
            usersInfo.add(userMapper.userEntityToInfoDto(user));
        }
        return usersInfo;
    }

    public void changePassword(Long id, String password) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setPassword(passwordEncoder.encode(password));
    }

    //TODO implement admin method for deleting users
}
