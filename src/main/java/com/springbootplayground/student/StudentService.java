package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    public abstract Student getStudentById(UUID studentId);
    public abstract List<Student> getAllStudents();
    public abstract Student createStudent(StudentCreateDto student);
    public abstract void updateStudent(Student student);
    public abstract void deleteStudentById(UUID studentId);
}
