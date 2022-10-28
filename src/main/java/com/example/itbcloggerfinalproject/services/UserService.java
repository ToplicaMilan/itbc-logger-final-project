package com.example.itbcloggerfinalproject.services;

import com.example.itbcloggerfinalproject.domain.LogEntity;
import com.example.itbcloggerfinalproject.domain.RoleType;
import com.example.itbcloggerfinalproject.domain.UserEntity;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    private static final List<String> DEFAULT_ROLES = List.of("USER");
    private static final List<String> ADMIN_ROLES = List.of("USER", "ADMIN");

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

    public void createLog(LogDTO dto) {
        LogEntity log = logRepository.save(logMapper.logDtoToEntity(dto));
//        log.setCreatedDate(java.util.Date.from(Instant.now()));
    }
}
