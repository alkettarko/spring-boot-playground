package com.springbootplayground.student;

import com.springbootplayground.student.dto.StudentCreateDto;
import com.springbootplayground.student.dto.StudentUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping(value = "/{studentId}")
    public ResponseEntity<Student> getById(@PathVariable("studentId") UUID studentId) {
        return ResponseEntity.ok(studentService.getById(studentId));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody StudentCreateDto studentCreateDto) {
        return ResponseEntity.ok(studentService.create(studentCreateDto));
    }

    @PutMapping(value = "/{studentId}")
    public ResponseEntity<Student> update(
            @PathVariable UUID studentId,
            @RequestBody StudentUpdateDto studentUpdateDto) {
        return ResponseEntity.ok(studentService.update(studentId, studentUpdateDto));
    }

    @DeleteMapping(value = "/{studentId}")
    public ResponseEntity delete(@PathVariable UUID studentId) {
        studentService.deleteById(studentId);
        return ResponseEntity.ok().build();
    }

}
