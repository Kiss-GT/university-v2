/*
package com.kgt.university.services;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.commands.StudentCommand;
import com.kgt.university.converters.*;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.domain.Student;
import com.kgt.university.repositories.CourseRepo;
import com.kgt.university.repositories.ProfRepo;
import com.kgt.university.repositories.StudRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UniversityServiceImpl implements UniversityService{

    private final CourseRepo courseRepo;
    private final ProfRepo profRepo;
    private final StudRepo studRepo;
    private final StudentCommandToStudent studentCommandToStudent;
    private final StudentToStudentCommand studentToStudentCommand;
    private final CourseToCourseCommand courseToCourseCommand;
    private final CourseCommandToCourse courseCommandToCourse;
    private final ProfessorCommandToProfessor professorCommandToProfessor;
    private final ProfessorToProfessorCommand professorToProfessorCommand;

    public UniversityServiceImpl(CourseRepo courseRepo, ProfRepo profRepo, StudRepo studRepo, StudentCommandToStudent studentCommandToStudent, StudentToStudentCommand studentToStudentCommand, CourseToCourseCommand courseToCourseCommand, CourseCommandToCourse courseCommandToCourse, ProfessorCommandToProfessor professorCommandToProfessor, ProfessorToProfessorCommand professorToProfessorCommand) {
        this.courseRepo = courseRepo;
        this.profRepo = profRepo;
        this.studRepo = studRepo;
        this.studentCommandToStudent = studentCommandToStudent;
        this.studentToStudentCommand = studentToStudentCommand;
        this.courseToCourseCommand = courseToCourseCommand;
        this.courseCommandToCourse = courseCommandToCourse;

        this.professorCommandToProfessor = professorCommandToProfessor;
        this.professorToProfessorCommand = professorToProfessorCommand;
    }
    @Override
    public Set<Course> getCourses() {
        Set<Course>courseSet=new HashSet<>();
        courseRepo.findAll().iterator().forEachRemaining(courseSet::add);
        return courseSet;
    }
    @Override
    public Set<Professor> getProfessors() {
        Set<Professor>professorSet=new HashSet<>();
        profRepo.findAll().iterator().forEachRemaining(professorSet::add);
        return professorSet;
    }
    @Override
    public Set<Student> getStudents() {
        Set<Student>studentSet=new HashSet<>();
        studRepo.findAll().iterator().forEachRemaining(studentSet::add);
        return studentSet;
    }
    @Override
    public Professor findProfessorById(Long l) {
        Optional<Professor> detailsOptional=profRepo.findById(l);
        if(!detailsOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found");
        }
        return detailsOptional.get();
    }
    @Override
    @Transactional
    public StudentCommand saveStudentCommand(StudentCommand command) {
        Student detachedStudent=studentCommandToStudent.convert(command);
        Student savedStudent=studRepo.save(detachedStudent);
        log.debug("Saved RecipeId:" + savedStudent.getId());
        return studentToStudentCommand.convert(savedStudent);
    }

    @Override
    public ProfessorCommand saveProfessorCommand(ProfessorCommand command) {
        Professor detachedProfessor=professorCommandToProfessor.convert(command);
        Professor savedProfessor=profRepo.save(detachedProfessor);
        log.debug("Saved RecipeId:" + savedProfessor.getId());
        return professorToProfessorCommand.convert(savedProfessor);
    }
    @Override
    public CourseCommand saveCourseCommand2(CourseCommand command) {
        Course detachedCourse=courseCommandToCourse.convert(command);
        Course savedCourse=courseRepo.save(detachedCourse);
        log.debug("Saved RecipeId:" + savedCourse.getId());
        return courseToCourseCommand.convert(savedCourse);
    }

    @Override
    @Transactional
    public CourseCommand saveCourseCommand(CourseCommand command) {
        Optional<Professor> professorOptional = profRepo.findById(command.getProfessorId());
        if(professorOptional.isPresent()){
            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getProfessorId());
            System.out.println("Recipe not found for id: " + command.getProfessorId());
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
    public Student findStudentById(Long l) {
        Optional<Student> studentOptional=studRepo.findById(l);
        if(!studentOptional.isPresent()){
            throw new RuntimeException("Student Not Found");
        }
        return studentOptional.get();
    }
    @Transactional
    @Override
    public StudentCommand findStudentCommandById(Long l) {
        return studentToStudentCommand.convert(findStudentById(l));
    }

    @Transactional
    @Override
    public ProfessorCommand findProfessorCommandById(Long l) {
        return professorToProfessorCommand.convert(findProfessorById(l));
    }

    @Override
    public Student findById(Long l) {
        Optional<Student> studentOptional=studRepo.findById(l);
        if(!studentOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found");
        }
        return studentOptional.get();
    }
    @Override
    public void deleteStudentById(Long idToDelete) {
        studRepo.deleteById(idToDelete);
    }
    @Override
    public void deleteProfessorById(Long idToDelete) {
        profRepo.deleteById(idToDelete);
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
}
*/
