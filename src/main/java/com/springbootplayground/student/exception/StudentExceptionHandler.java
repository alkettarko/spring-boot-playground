package com.springbootplayground.student.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(value = StudentApplicationException.class)
    public ResponseEntity<Object> exception(StudentApplicationException exception) {
        return new ResponseEntity<>(exception, exception.getStatus());
    }
}
