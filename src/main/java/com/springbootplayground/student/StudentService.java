package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    public abstract Student getStudentById(UUID studentId);

    public abstract List<Student> getAllStudents();

    public abstract Student createStudent(StudentCreateDto student);

    public abstract Student updateStudent(UUID studentId, StudentUpdateDto studentUpdateDto);

    public abstract void deleteStudentById(UUID studentId);
}
