package com.kgt.university.services;

import com.kgt.university.commands.StudentCommand;
import com.kgt.university.converters.StudentCommandToStudent;
import com.kgt.university.converters.StudentToStudentCommand;
import com.kgt.university.domain.Student;
import com.kgt.university.repositories.StudRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service

public class StudentServiceImpl implements StudentService{

    private final StudRepo studRepo;
    private final StudentCommandToStudent studentCommandToStudent;
    private final StudentToStudentCommand studentToStudentCommand;

    public StudentServiceImpl(StudRepo studRepo, StudentCommandToStudent studentCommandToStudent, StudentToStudentCommand studentToStudentCommand) {
        this.studRepo = studRepo;
        this.studentCommandToStudent = studentCommandToStudent;
        this.studentToStudentCommand = studentToStudentCommand;
    }

    @Override
    public Set<Student> getStudents() {
        Set<Student>studentSet=new HashSet<>();
        studRepo.findAll().iterator().forEachRemaining(studentSet::add);
        return studentSet;
    }

    @Override
    public StudentCommand saveStudentCommand(StudentCommand command) {
        Student detachedStudent=studentCommandToStudent.convert(command);
        Student savedStudent=studRepo.save(detachedStudent);
        log.debug("Saved RecipeId:" + savedStudent.getId());
        return studentToStudentCommand.convert(savedStudent);
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
    public Student findStudentById(Long l) {
        Optional<Student> studentOptional=studRepo.findById(l);
        if(!studentOptional.isPresent()){
            throw new RuntimeException("Student Not Found");
        }
        return studentOptional.get();
    }

    @Override
    public StudentCommand findStudentCommandById(Long l) {
        return studentToStudentCommand.convert(findStudentById(l));
    }

    @Override
    public void deleteStudentById(Long idToDelete) {
        studRepo.deleteById(idToDelete);
    }
}
