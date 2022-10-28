package com.example.itbcloggerfinalproject.domain.dtos;

import com.example.itbcloggerfinalproject.domain.LogType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LogDTO {

    private String userLog;
    private LogType logType;
}
