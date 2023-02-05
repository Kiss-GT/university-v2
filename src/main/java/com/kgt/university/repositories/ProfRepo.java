package com.kgt.university.repositories;


import com.kgt.university.domain.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfRepo extends CrudRepository<Professor, Long> {
}
