package com.example.classservice.mapper;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.model.Class;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-04T14:26:41+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.12 (Eclipse Adoptium)"
)
@Component
public class ClassMapperImpl implements ClassMapper {

    @Override
    public ClassDTO toDto(Class entity) {
        if ( entity == null ) {
            return null;
        }

        ClassDTO classDTO = new ClassDTO();

        classDTO.setId( entity.getId() );
        classDTO.setName( entity.getName() );
        classDTO.setTeacherId( entity.getTeacherId() );
        classDTO.setTeacherName( entity.getTeacherName() );

        return classDTO;
    }

    @Override
    public Class toEntity(ClassDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Class class1 = new Class();

        class1.setId( dto.getId() );
        class1.setName( dto.getName() );
        class1.setTeacherId( dto.getTeacherId() );
        class1.setTeacherName( dto.getTeacherName() );

        return class1;
    }
}
