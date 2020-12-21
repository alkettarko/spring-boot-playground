package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentService studentService;


    @AfterEach
    void tearDown() {
        studentService.deleteAll();
    }

    @Test
    public void should_create_student() {
        // GIVEN
        StudentCreateDto studentCreateDto = new StudentCreateDto("Alket", "Tarko", "alket@gmail.com", "Tirana, Albania");

        // WHEN
        ResponseEntity<Student> student = studentController.create(studentCreateDto);

        // THEN
        then(student != null);
        assert student != null;
        then(student.getStatusCode().is2xxSuccessful());
        then(student.getBody() != null);
        then(student.getBody().getFirstName().contentEquals("Alket"));
        then(student.getBody().getLastName().contentEquals("Tarko"));
        then(student.getBody().getAddress().contentEquals("Tirana, Albania"));
    }

    @Test
    public void should_find_all_students() {
        // GIVEN
        persistTwoStudents();

        // WHEN
        ResponseEntity<List<Student>> students = studentController.getAll();

        // THEN
        then(students.getStatusCode().is2xxSuccessful());
        then(students.getBody().size() == 2);
    }

    @Test
    public void should_update_student() {
        // GIVEN
        persistTwoStudents();
        Student student = studentService.getByEmail("jdoe@gmail.com");
        UUID studentId = student.getId();
        StudentUpdateDto studentUpdateDto = new StudentUpdateDto("someone@outlook.com", "Rome, Italy");

        // WHEN
        ResponseEntity<Student> updatedStudent = studentController.update(studentId, studentUpdateDto);

        // THEN
        then(updatedStudent.getStatusCode().is2xxSuccessful());
        then(updatedStudent.getBody().getAddress().contentEquals(studentUpdateDto.getAddress()));
        then(updatedStudent.getBody().getEmail().contentEquals(studentUpdateDto.getEmail()));
    }

    @Test
    public void should_delete_student() {
        // GIVEN
        studentController.create(new StudentCreateDto("Kevin", "Hart", "kevhart@gmail.com", "Tirana, Albania"));

        // WHEN
        Student student = studentService.getByEmail("kevhart@gmail.com");
        UUID studentId = student.getId();
        ResponseEntity responseEntity = studentController.delete(studentId);

        // THEN
        then(responseEntity.getStatusCode().is2xxSuccessful());
    }

    private void persistTwoStudents() {
        studentController.create(new StudentCreateDto("John", "Doe", "jdoe@gmail.com", "Tirana, Albania"));
        studentController.create(new StudentCreateDto("Jack", "Sparrow", "sparrow@gmail.com", "Tirana, Albania"));
    }

}