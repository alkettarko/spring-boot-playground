package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

//    @GetMapping("/")
//    public String getGreeting(){
//        return "helllooooo";
//    }

    @GetMapping(value = "/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("studentId")UUID studentId){
        return ResponseEntity.ok(studentService.getStudentById(studentId));
    }

    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Student> createStudent(@RequestBody StudentCreateDto studentCreateDto){
        return ResponseEntity.ok(studentService.createStudent(studentCreateDto));
    }

}
