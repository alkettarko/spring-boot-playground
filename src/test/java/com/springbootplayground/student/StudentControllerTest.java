package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentService studentService;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @AfterEach
    void tearDown() {
        studentService.deleteAll();
    }

//    @Test
//    public void givenWac_whenServletContext_thenItProvidesGreetController() {
//        ServletContext servletContext = wac.getServletContext();
//
//
//        Assertions.assertNotNull(servletContext);
//        Assertions.assertTrue(servletContext instanceof MockServletContext);
//        Assertions.assertNotNull(wac.getBean("greetController"));
//    }

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
    public void should_find_all_students() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/students"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello World!!!"))
                .andReturn();

        Assertions.assertEquals("application/json;charset=UTF-8",
                mvcResult.getResponse().getContentType());


//        // GIVEN
//        persistTwoStudents();
//
//        // WHEN
//        ResponseEntity<List<Student>> students = studentController.getAll();
//
//        // THEN
//        then(students.getStatusCode().is2xxSuccessful());
//        then(students.getBody().size() == 2);
    }

    @Test
    public void should_update_student() {
        // GIVEN
        persistTwoStudents();
        Optional<Student> student = studentService.getByEmail("jdoe@gmail.com");
        UUID studentId = student.get().getId();
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
        Optional<Student> student = studentService.getByEmail("kevhart@gmail.com");
        UUID studentId = student.get().getId();
        ResponseEntity responseEntity = studentController.delete(studentId);

        // THEN
        then(responseEntity.getStatusCode().is2xxSuccessful());
    }

    private void persistTwoStudents() {
        studentController.create(new StudentCreateDto("John", "Doe", "jdoe@gmail.com", "Tirana, Albania"));
        studentController.create(new StudentCreateDto("Jack", "Sparrow", "sparrow@gmail.com", "Tirana, Albania"));
    }

}