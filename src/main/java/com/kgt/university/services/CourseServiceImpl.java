package com.kgt.university.services;


import com.kgt.university.commands.CourseCommand;
import com.kgt.university.converters.CourseCommandToCourse;
import com.kgt.university.converters.CourseToCourseCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.repositories.CourseRepo;
import com.kgt.university.repositories.ProfRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService{


    private final CourseRepo courseRepo;
    private final ProfRepo profRepo;
    private final CourseCommandToCourse courseCommandToCourse;
    private final CourseToCourseCommand courseToCourseCommand;

    public CourseServiceImpl(CourseRepo courseRepo, ProfRepo profRepo, CourseCommandToCourse courseCommandToCourse, CourseToCourseCommand courseToCourseCommand) {
        this.courseRepo = courseRepo;
        this.profRepo = profRepo;
        this.courseCommandToCourse = courseCommandToCourse;
        this.courseToCourseCommand = courseToCourseCommand;
    }


    @Override
    public Set<Course> getCourses() {
        Set<Course>courseSet=new HashSet<>();
        courseRepo.findAll().iterator().forEachRemaining(courseSet::add);
        return courseSet;
    }

    @Override
    @Transactional
    public CourseCommand saveCourseCommand(CourseCommand command) {
        Optional<Professor> professorOptional = profRepo.findById(command.getProfessorId());
        if(!professorOptional.isPresent()){
            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getProfessorId());

            return new CourseCommand();
        } else {
            Professor professor = professorOptional.get();
            Optional<Course> courseOptional = professor
                    .getCourses()
                    .stream()
                    .filter(course -> course.getId().equals(command.getId()))
                    .findFirst();
            if(courseOptional.isPresent()){
                Course courseFound = courseOptional.get();
                courseFound.setName(command.getName());
                courseFound.setDescription(command.getDescription());

            } else {

                Course course = courseCommandToCourse.convert(command);
                course.setProfessor(professor);
                professor.addCourse(course);
            }
            Professor savedProfessor = profRepo.save(professor);
            Optional<Course> savedCourseOptional = savedProfessor.getCourses().stream()
                    .filter(professorCourses -> professorCourses.getId().equals(command.getId()))
                    .findFirst();
            //check by description
            if(!savedCourseOptional.isPresent()){
                //not totally safe... But best guess
                savedCourseOptional = savedProfessor.getCourses().stream()
                        .filter(professorCourses -> professorCourses.getName().equals(command.getName()))
                        .filter(professorCourses -> professorCourses.getDescription().equals(command.getDescription()))
                        .findFirst();
            }
            //to do check for fail
            return courseToCourseCommand.convert(savedCourseOptional.get());
        }
    }

    @Override
    public void deleteCourseById(Long professorId, Long idToDelete) {
        log.debug("Deleting ingredient: " + professorId + ":" + idToDelete);

        Optional<Professor> professorOptional = profRepo.findById(professorId);

        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();
            log.debug("found recipe");

            Optional<Course> courseOptional = professor
                    .getCourses()
                    .stream()
                    .filter(course -> course.getId().equals(idToDelete))
                    .findFirst();

            if(courseOptional.isPresent()){
                log.debug("found Ingredient");
                Course courseToDelete = courseOptional.get();
                courseToDelete.setProfessor(null);
                professor.getCourses().remove(courseOptional.get());
                profRepo.save(professor);
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + professorId);
        }

    }

    @Override
    public CourseCommand findByProfessorAndCourseId(Long profId, Long courseId) {
        Optional<Professor> professorOptional = profRepo.findById(profId);
        if (!professorOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + profId);
        }
        Professor professor = professorOptional.get();
        Optional<CourseCommand> courseCommandOptional = professor.getCourses().stream()
                .filter(course -> course.getId().equals(courseId))
                .map( course -> courseToCourseCommand.convert(course)).findFirst();

        if(!courseCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + courseId);
        }

        return courseCommandOptional.get();
    }
}
