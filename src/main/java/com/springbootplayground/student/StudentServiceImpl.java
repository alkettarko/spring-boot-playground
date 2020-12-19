package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
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
    public Student updateStudent(UUID studentId, StudentUpdateDto studentUpdateDto) {
        checkIfStudentExists(studentId);
        checkIfExistsByEmail(studentUpdateDto.getEmail());
        Student student = studentRepository.getOne(studentId);
        studentMapper.copy(studentUpdateDto, student);
        studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    public void deleteStudentById(UUID studentId) {
        checkIfStudentExists(studentId);
        studentRepository.deleteById(studentId);
    }

    private void checkIfStudentExists(UUID studentId) {
        if (!studentRepository.existsByID(studentId)) {
            throw new StudentException("Student with id: " + studentId + " does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    private void checkIfExistsByEmail(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new StudentException(
                    "Student with email: " + email + " already exists."
                    , HttpStatus.BAD_REQUEST);
        }
    }
}
