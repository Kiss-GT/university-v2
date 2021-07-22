package com.kgt.university.services;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.commands.StudentCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.domain.Student;

import java.util.Set;

public interface StudentService {

    Set<Student> getStudents();

    StudentCommand saveStudentCommand(StudentCommand command);

    Student findById(Long l);

    Student findStudentById(Long l);

    StudentCommand findStudentCommandById(Long l);

    void deleteStudentById(Long idToDelete);


}
