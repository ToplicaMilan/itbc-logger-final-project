package com.example.itbcloggerfinalproject.bootstrap;

import com.example.itbcloggerfinalproject.domain.entities.LogEntity;
import com.example.itbcloggerfinalproject.domain.entities.RoleType;
import com.example.itbcloggerfinalproject.domain.entities.UserEntity;
import com.example.itbcloggerfinalproject.repositories.LogRepository;
import com.example.itbcloggerfinalproject.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class BootStrap implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LogRepository logRepository;

    @Override
    public void run(String... args) throws Exception {

        UserEntity admin = new UserEntity(1L, "ToplicaMilan", "milan.wittg@gmail.com"
                , "Cnszdg91", RoleType.ADMIN, new ArrayList<>());
        userRepository.save(admin);

    }
}
