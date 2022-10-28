package com.example.itbcloggerfinalproject.repositories;

import com.example.itbcloggerfinalproject.domain.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
