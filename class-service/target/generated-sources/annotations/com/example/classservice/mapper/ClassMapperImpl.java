package com.example.classservice.mapper;

import com.example.classservice.dto.ClassDTO;
import com.example.classservice.model.Class;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T16:43:01+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Eclipse Adoptium)"
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
        Set<String> set = entity.getStudentIds();
        if ( set != null ) {
            classDTO.setStudentIds( new LinkedHashSet<String>( set ) );
        }

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
        Set<String> set = dto.getStudentIds();
        if ( set != null ) {
            class1.setStudentIds( new LinkedHashSet<String>( set ) );
        }

        return class1;
    }
}
