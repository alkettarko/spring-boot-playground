package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import com.springbootplayground.student.exception.StudentApplicationException;
import com.springbootplayground.student.mapper.StudentMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Student getById(@NonNull UUID studentId) {
        checkIfStudentExists(studentId);
        Student student = studentRepository.getOne(studentId);
        return student;
    }

    @Override
    public Optional<Student> getByEmail(@NonNull String email) {

        return Optional.of(studentRepository.getByEmail(email)
                .orElseThrow(() -> {
                    throw new StudentApplicationException("Student with email: " + email + " does not exist."
                            , HttpStatus.NOT_FOUND);
                }));
    }

    @Override
    public List<Student> getAll() {
        List<Student> students = studentRepository.findAll();
        return students;
    }

    @Override
    public Student create(@NonNull StudentCreateDto studentCreateDto) {
        checkIfExistsByEmail(studentCreateDto.getEmail());
        Student student = studentMapper.toEntity(studentCreateDto);
        studentRepository.saveAndFlush(student);
        return student;
    }

    @Override
    @Transactional
    public Student update(@NonNull UUID studentId, @NonNull StudentUpdateDto studentUpdateDto) {
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

    private void checkIfStudentExists(UUID studentId) {
        if (!studentRepository.existsById(studentId)) {
            log.debug("Student with id {} does not exist!, studentId");
            throw new StudentApplicationException("Student with id: " + studentId + " does not exist!"
                    , HttpStatus.NOT_FOUND);
        }
    }

    private void checkIfExistsByEmail(String email) {
        if (studentRepository.existsByEmail(email)) {
            log.debug("Student with email {} already exists!, email");
            throw new StudentApplicationException(
                    "Student with email: " + email + " already exists."
                    , HttpStatus.BAD_REQUEST);
        }
    }
}
