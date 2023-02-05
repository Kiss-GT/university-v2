package com.kgt.university.services;



import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.repositories.CourseRepo;


import com.kgt.university.repositories.ProfRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class CourseService{


    private final CourseRepo courseRepo;
    private final ProfRepo profRepo;


    public CourseService(CourseRepo courseRepo, ProfRepo profRepo) {
        this.courseRepo = courseRepo;
        this.profRepo = profRepo;
       
    }


    public List<Course> getCourses() {
        return (List<Course>) courseRepo.findAll();
    }



/*    public Course findByProfessorAndCourseId(Long profId, Long courseId) {
        Optional<Professor> professorOptional = profRepo.findById(profId);
        Professor professor = professorOptional.get();
        Optional<Course> courseCommandOptional = professor.getCourses().stream()
                .filter(course -> course.getId().equals(courseId));
        return courseCommandOptional.get();
    }*/

    public void saveCourse(Course course) {
        courseRepo.save(course);
    }

    public void deleteCourseById(Long valueOf) {
        courseRepo.deleteById(valueOf);
    }

    public Course findCourseById(Long l) {
        Optional<Course> detailsOptional=courseRepo.findById(l);
        if(!detailsOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found");
        }
        return detailsOptional.get();
    }
    public List<Professor> listProfessors() {
        return (List<Professor>) profRepo.findAll();
    }
}
