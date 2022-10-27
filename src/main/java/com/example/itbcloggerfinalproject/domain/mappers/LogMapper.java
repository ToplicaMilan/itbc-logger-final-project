package com.example.itbcloggerfinalproject.domain.mappers;

import com.example.itbcloggerfinalproject.domain.LogEntity;
import com.example.itbcloggerfinalproject.domain.dtos.LogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LogMapper {

//    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);
    @Mapping(target = "id", ignore = true)
    public abstract LogEntity logDtoToEntity(LogDTO dto);

    public abstract LogDTO logEntityToDto(LogEntity log);
}
