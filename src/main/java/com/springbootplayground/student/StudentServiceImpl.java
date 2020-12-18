package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.exception.StudentException;
import com.springbootplayground.student.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getStudentById(UUID studentId) {
        checkIfStudentExists(studentId);
        Student student = studentRepository.getOne(studentId);
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students;
    }

    @Override
    public Student createStudent(StudentCreateDto studentCreateDto) {
        Student student = studentMapper.toEntity(studentCreateDto);
        studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudentById(UUID studentId) {

    }

    private void checkIfStudentExists(UUID studentId) {
        if (!studentRepository.existsByID(studentId)) {
            throw new StudentException("Student with id: " + studentId + " does not exist!", HttpStatus.NOT_FOUND);
        }
    }
}
