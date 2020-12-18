package com.springbootplayground.student.mapper;

import com.springbootplayground.student.Student;
import com.springbootplayground.student.dto.StudentCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    Student toEntity(StudentCreateDto studentCreateDto);
}
