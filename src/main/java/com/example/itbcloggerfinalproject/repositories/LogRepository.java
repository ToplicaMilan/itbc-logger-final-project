package com.example.itbcloggerfinalproject.repositories;

import com.example.itbcloggerfinalproject.domain.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
