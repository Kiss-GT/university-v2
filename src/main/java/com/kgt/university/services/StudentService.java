package com.kgt.university.services;




import com.kgt.university.domain.Student;
import com.kgt.university.repositories.StudRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class StudentService {

    private final StudRepo studRepo;

    public StudentService(StudRepo studRepo) {
        this.studRepo = studRepo;
    }

    public Set<Student> getStudents() {
        Set<Student>studentSet=new HashSet<>();
        studRepo.findAll().iterator().forEachRemaining(studentSet::add);
        return studentSet;
    }


    public Student saveStudent(Student command) {
        Student savedStudent=studRepo.save(command);
        return savedStudent;
    }

    public Student findStudentById(Long l) {
        Optional<Student> studentOptional=studRepo.findById(l);
        if(!studentOptional.isPresent()){
            throw new RuntimeException("Student Not Found");
        }
        return studentOptional.get();
    }

    public void deleteStudentById(Long idToDelete) {
        studRepo.deleteById(idToDelete);
    }
}
