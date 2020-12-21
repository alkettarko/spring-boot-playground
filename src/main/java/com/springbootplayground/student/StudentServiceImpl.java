package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import com.springbootplayground.student.exception.StudentException;
import com.springbootplayground.student.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Student getById(UUID studentId) {
        checkIfStudentExists(studentId);
        Student student = studentRepository.getOne(studentId);
        return student;
    }

    @Override
    public Student getByEmail(String email) {
        Student student = studentRepository.getByEmail(email);
        if (student == null) {
            throw new StudentException("Student with email: " + email + " does not exist."
                    , HttpStatus.NOT_FOUND);
        }
        return student;
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = studentRepository.findAll();
        return students;
    }

    @Override
    public Student create(StudentCreateDto studentCreateDto) {
        checkIfExistsByEmail(studentCreateDto.getEmail());
        Student student = studentMapper.toEntity(studentCreateDto);
        studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    @Transactional
    public Student update(UUID studentId, StudentUpdateDto studentUpdateDto) {
        checkIfStudentExists(studentId);
        checkIfExistsByEmail(studentUpdateDto.getEmail());
        Student student = studentRepository.getOne(studentId);
        studentMapper.copy(studentUpdateDto, student);
        studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    public void deleteById(UUID studentId) {
        checkIfStudentExists(studentId);
        studentRepository.deleteById(studentId);
    }

    @Override
    public void deleteAll() {
        studentRepository.deleteAll();
    }

    private void checkIfStudentExists(UUID studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentException("Student with id: " + studentId + " does not exist!"
                    , HttpStatus.NOT_FOUND);
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
