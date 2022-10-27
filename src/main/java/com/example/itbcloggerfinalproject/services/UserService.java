package com.example.itbcloggerfinalproject.services;

import com.example.itbcloggerfinalproject.domain.UserEntity;
import com.example.itbcloggerfinalproject.domain.dtos.UserCreationDTO;
import com.example.itbcloggerfinalproject.domain.mappers.UserMapper;
import com.example.itbcloggerfinalproject.exceptions.InvalidCredentialsException;
import com.example.itbcloggerfinalproject.exceptions.InvalidEmailException;
import com.example.itbcloggerfinalproject.exceptions.InvalidUsernameException;
import com.example.itbcloggerfinalproject.repositories.UserRepository;
import com.example.itbcloggerfinalproject.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;

    private static final List<String> DEFAULT_ROLES = List.of("USER");
    private static final List<String> ADMIN_ROLES = List.of("USER", "ADMIN");

    public String createUser(UserCreationDTO dto) {
        if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
            throw new InvalidEmailException("Provided email not valid!");
        }
        if (repository.existsByUsername(dto.getUsername())) {
            throw new InvalidUsernameException("Username is taken!");
        }
        //TODO implement password validation
        UserEntity user = repository.save(mapper.userCreationDtoToEntity(dto));
        return tokenProvider.create(user, DEFAULT_ROLES);
    }

    @Transactional(readOnly = true)
    public String provideToken(String username, String password) {
        UserEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password!"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password!");
        }
        if (user.getRole().toString().equals("ADMIN")) {
            return tokenProvider.create(user, ADMIN_ROLES);
        }
        return tokenProvider.create(user, DEFAULT_ROLES);
    }
}
