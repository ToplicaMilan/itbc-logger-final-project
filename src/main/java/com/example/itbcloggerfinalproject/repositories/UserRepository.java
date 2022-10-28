package com.example.itbcloggerfinalproject.repositories;

import com.example.itbcloggerfinalproject.domain.entities.LogEntity;
import com.example.itbcloggerfinalproject.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
}
