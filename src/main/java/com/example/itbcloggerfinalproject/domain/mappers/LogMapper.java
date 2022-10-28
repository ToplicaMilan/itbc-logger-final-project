package com.example.itbcloggerfinalproject.domain.mappers;

import com.example.itbcloggerfinalproject.domain.LogEntity;
import com.example.itbcloggerfinalproject.domain.dtos.LogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper
public abstract class LogMapper {

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
    public LogEntity logDtoToEntity(LogDTO dto){
         return LogEntity.builder()
                .userLog(dto.getUserLog())
                .logType(dto.getLogType())
                .createdDate(java.util.Date.from(Instant.now()))
                .build();
    }


    public abstract LogDTO logEntityToDto(LogEntity log);
}
