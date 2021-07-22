package com.kgt.university.repositories;

import com.kgt.university.domain.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudRepo extends CrudRepository<Student, Long> {
}
