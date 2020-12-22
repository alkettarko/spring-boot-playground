package com.springbootplayground.student.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String errorMessage;
    private HttpStatus status;
}
