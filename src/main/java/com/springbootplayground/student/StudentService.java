package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    Student getById(UUID studentId);

    Student getByEmail(String email);

    List<Student> getAll();

    Student create(StudentCreateDto student);

    Student update(UUID studentId, StudentUpdateDto studentUpdateDto);

    void deleteById(UUID studentId);

    void deleteAll();
}
