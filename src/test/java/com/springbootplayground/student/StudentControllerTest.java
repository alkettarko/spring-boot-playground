package com.springbootplayground.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class StudentControllerTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private Student johnDoe;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }


    @Test
    @SneakyThrows
    public void should_create_student() {

        // GIVEN
        var studentCreateDto = new StudentCreateDto(
                "Alket",
                "Tarko",
                "alket@yahoo.com",
                "Tirana, Albania");

        var jsonRequest = objectMapper.writeValueAsString(studentCreateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                post("/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());

        // THEN
        mvcResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(studentCreateDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(studentCreateDto.getLastName())));

    }

    @Test
    @SneakyThrows
    public void should_fail_create_if_mandatory_properties_are_not_given_in_the_request() {
        // GIVEN
        var studentCreateDto = new StudentCreateDto();
        var jsonRequest = objectMapper.writeValueAsString(studentCreateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                post("/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());

        // THEN
        mvcResult.andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void should_fail_create_if_email_is_invalid() {
        // GIVEN
        var studentCreateDto = new StudentCreateDto(
                "Alket",
                "Tarko",
                "alket.invalid.email.com",
                "Tirana, Albania");

        var jsonRequest = objectMapper.writeValueAsString(studentCreateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                post("/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());

        // THEN
        mvcResult.andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void should_fail_create_if_student_with_given_email_already_exists() {
        // GIVEN
        persistTwoStudents();
        var studentCreateDto = new StudentCreateDto(
                "Jeff",
                "Doe",
                "jdoe@gmail.com",
                "Paris, France");

        var jsonRequest = objectMapper.writeValueAsString(studentCreateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                post("/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());

        // THEN
        mvcResult.andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void should_find_student_by_id() {
        // GIVEN
        persistTwoStudents();

        // WHEN
        var mvcResult = this.mockMvc.perform(get("/students/{studentId}", johnDoe.getId()))
                .andDo(print());

        // THEN
        mvcResult
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.email", is("jdoe@gmail.com")))
                .andExpect(jsonPath("$.address", is("New York, US")));

    }

    @Test
    @SneakyThrows
    public void should_find_all_students() {
        // GIVEN
        persistTwoStudents();

        // WHEN
        var mvcResult = this.mockMvc.perform(get("/students"))
                .andDo(print());

        // THEN
        mvcResult
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("John", "Jack")));

    }

    @Test
    @SneakyThrows
    public void should_update_student() {
        // GIVEN
        persistTwoStudents();
        StudentUpdateDto studentUpdateDto = new StudentUpdateDto("someone@outlook.com", "Rome, Italy");
        var jsonRequest = objectMapper.writeValueAsString(studentUpdateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                put("/students/{studentId}", johnDoe.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());

        // THEN
        mvcResult.andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(studentUpdateDto.getEmail())))
                .andExpect(jsonPath("$.address", is(studentUpdateDto.getAddress())));

    }

    @Test
    @SneakyThrows
    public void should_fail_update_if_email_is_invalid() {
        // GIVEN
        persistTwoStudents();
        StudentUpdateDto studentUpdateDto = new StudentUpdateDto("johndoe.invalid-email", "Milan, Italy");
        var jsonRequest = objectMapper.writeValueAsString(studentUpdateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                put("/students/{studentId}", johnDoe.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());


        // THEN
        mvcResult.andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void should_fail_update_if_new_email_alredy_exists() {
        // GIVEN
        persistTwoStudents();
        StudentUpdateDto studentUpdateDto = new StudentUpdateDto("sparrow@gmail.com", "Berlin, Germany");
        var jsonRequest = objectMapper.writeValueAsString(studentUpdateDto);

        // WHEN
        var mvcResult = mockMvc.perform(
                put("/students/{studentId}", johnDoe.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest))
                .andDo(print());

        // THEN
        mvcResult.andExpect(status().isBadRequest());

    }

    @Test
    @SneakyThrows
    public void should_delete_student() {
        // GIVEN
        persistTwoStudents();

        // WHEN
        var mvcResult = mockMvc.perform(
                delete("/students/{studentId}", johnDoe.getId()));

        // THEN
        mvcResult
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        then(studentRepository.getByEmail(johnDoe.getEmail()).isEmpty());

    }

    @Test
    @SneakyThrows
    public void should_fail_delete_if_student_not_found() {
        // GIVEN
        UUID studentId = UUID.randomUUID(); // The student with this ID does not exist

        // WHEN
        var mvcResult = mockMvc.perform(
                delete("/students/{studentId}", studentId));

        // THEN
        mvcResult.andExpect(status().isNotFound());

    }


    private void persistTwoStudents() {
        // Saved this as an instance to use it's ID for tests that require an ID as a path variable
        this.johnDoe = studentRepository.saveAndFlush(
                new Student(
                        UUID.randomUUID(),
                        "John",
                        "Doe",
                        "jdoe@gmail.com",
                        "New York, US",
                        LocalDateTime.now()));

        studentRepository.saveAndFlush(
                new Student(
                        UUID.randomUUID(),
                        "Jack",
                        "Sparrow",
                        "sparrow@gmail.com",
                        "Miami, Florida",
                        LocalDateTime.now()));
    }

}