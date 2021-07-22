package com.kgt.university.services;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.commands.StudentCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.domain.Student;

import java.util.Set;

public interface ProfService {

    Set<Professor> getProfessors();

    Professor findProfessorById(Long l);

    ProfessorCommand saveProfessorCommand(ProfessorCommand command);

    ProfessorCommand findProfessorCommandById(Long l);

    void deleteProfessorById(Long idToDelete);


}
