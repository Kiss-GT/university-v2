package com.kgt.university.services;

import com.kgt.university.commands.CourseCommand;

import com.kgt.university.domain.Course;


import java.util.Set;

public interface CourseService {
    Set<Course> getCourses();

    CourseCommand saveCourseCommand(CourseCommand command);

    void deleteCourseById(Long professorId, Long idToDelete);

    CourseCommand findByProfessorAndCourseId(Long profId, Long courseId);
}
