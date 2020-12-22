package com.springbootplayground.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsById(UUID id);

    boolean existsByEmail(String email);

    Optional<Student> getByEmail(String email);
}
