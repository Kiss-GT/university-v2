package com.kgt.university.repositories;


import com.kgt.university.domain.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepo extends CrudRepository<Course, Long> {
}
