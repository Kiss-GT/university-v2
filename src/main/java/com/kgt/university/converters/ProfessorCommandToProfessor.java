package com.kgt.university.converters;

import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.domain.Professor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProfessorCommandToProfessor implements Converter<ProfessorCommand, Professor> {

    public final CourseCommandToCourse courseCommandToCourse;

    public ProfessorCommandToProfessor( CourseCommandToCourse courseCommandToCourse) {
        this.courseCommandToCourse = courseCommandToCourse;
    }

    @Synchronized
    @Nullable
    @Override
    public Professor convert(ProfessorCommand source) {
        if(source == null) {
        return null;
        }

    final Professor professor = new Professor();
    professor.setId(source.getId());
    professor.setFirstName(source.getFirstName());
    professor.setLastName(source.getLastName());
    professor.setBio(source.getBio());
    professor.setImage(source.getImage());


        if (source.getCourses() != null && source.getCourses().size() > 0){
            source.getCourses()
                    .forEach(course -> professor.getCourses().add(courseCommandToCourse.convert(course)));
        }

        return professor;
    }
}

