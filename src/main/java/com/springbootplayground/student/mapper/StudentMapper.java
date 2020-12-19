package com.springbootplayground.student.mapper;

import com.springbootplayground.student.Student;
import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    Student toEntity(StudentCreateDto studentCreateDto);

    Student copy(StudentUpdateDto studentUpdateDto, @MappingTarget Student student);
}
