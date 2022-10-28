package com.example.itbcloggerfinalproject.repositories;

import com.example.itbcloggerfinalproject.domain.entities.LogEntity;
import com.example.itbcloggerfinalproject.domain.entities.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Long> {

    List<LogEntity> findAllByUserIdAndLogType(Long userId, LogType logType);
}
