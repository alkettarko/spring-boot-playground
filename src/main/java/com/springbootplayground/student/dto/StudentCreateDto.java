package com.springbootplayground.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateDto {

    @NotBlank(message = "First Name must not be empty")
    private String firstName;

    @NotBlank(message = "Last Name must not be empty")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email(message="Please provide a valid email address")
    private String email;

    @NotBlank(message = "Address must not be empty")
    private String address;

}
