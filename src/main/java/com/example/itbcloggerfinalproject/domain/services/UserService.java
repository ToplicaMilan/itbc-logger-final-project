package com.example.itbcloggerfinalproject.domain.services;

import com.example.itbcloggerfinalproject.domain.entities.LogEntity;
import com.example.itbcloggerfinalproject.domain.entities.LogType;
import com.example.itbcloggerfinalproject.domain.entities.RoleType;
import com.example.itbcloggerfinalproject.domain.entities.UserEntity;
import com.example.itbcloggerfinalproject.domain.dtos.LogDTO;
import com.example.itbcloggerfinalproject.domain.dtos.UserCreationDTO;
import com.example.itbcloggerfinalproject.domain.mappers.LogMapper;
import com.example.itbcloggerfinalproject.domain.mappers.UserMapper;
import com.example.itbcloggerfinalproject.exceptions.InvalidCredentialsException;
import com.example.itbcloggerfinalproject.exceptions.InvalidEmailException;
import com.example.itbcloggerfinalproject.exceptions.InvalidPasswordException;
import com.example.itbcloggerfinalproject.exceptions.InvalidUsernameException;
import com.example.itbcloggerfinalproject.repositories.LogRepository;
import com.example.itbcloggerfinalproject.repositories.UserRepository;
import com.example.itbcloggerfinalproject.security.PasswordValidation;
import com.example.itbcloggerfinalproject.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;
    public final UserMapper userMapper;
    public final LogMapper logMapper;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PasswordValidation passwordValidation;

    private static final List<String> DEFAULT_ROLES = List.of("ROLE_USER");
    private static final List<String> ADMIN_ROLES = List.of("USER", "ROLE_ADMIN");

    public String createUser(UserCreationDTO dto) {
        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            throw new InvalidEmailException("Provided email not valid!");
        }
        if (!passwordValidation.validate(dto.getPassword()).isValid()) {
            throw new InvalidPasswordException("Invalid password!");
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new InvalidUsernameException("Username is taken!");
        }
        UserEntity user = userRepository.save(userMapper.userCreationDtoToEntity(dto));
        user.setRole(RoleType.USER);
        return tokenProvider.create(user, DEFAULT_ROLES);
    }

    @Transactional(readOnly = true)
    public String provideToken(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password!");
        }
        if (user.getRole().toString().equals("ADMIN")) {
            return tokenProvider.create(user, ADMIN_ROLES);
        }
        return tokenProvider.create(user, DEFAULT_ROLES);
    }

    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password!"));
    }

    public void createLog(LogDTO dto, String username) {
        if (dto.getUserLog().length() > 1024) {
            throw new InvalidCredentialsException("Log message is too large!");
        }
        LogEntity log = logMapper.logDtoToEntity(dto);
        log.setUser(getUserByUsername(username));
        logRepository.save(log);
    }

    public List<LogDTO> getLogsByType(LogType logType) {
        List<LogDTO> logDtos = new ArrayList<>();
        for (LogEntity log : logRepository.findAll()) {
            logDtos.add(logMapper.logEntityToDto(log));
        }
        return logDtos;
    }
}
