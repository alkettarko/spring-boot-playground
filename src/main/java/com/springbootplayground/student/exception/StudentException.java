package com.springbootplayground.student.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String errorMessage;
    private HttpStatus status;
}
