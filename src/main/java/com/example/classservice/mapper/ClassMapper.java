// Package: com.example.classservice.mapper

package com.example.classservice.mapper;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.model.Class;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//TODO Use MapStruct mapper to map DTO to entity

@Mapper(componentModel = "spring")
public interface ClassMapper {

    @Mapping(target = "id", source = "entity.id")
    ClassDTO toDto(Class entity);

    @Mapping(target = "id", source = "dto.id")
    Class toEntity(ClassDTO dto);
}
