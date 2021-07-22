package com.kgt.university.converters;

import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.domain.Professor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class ProfessorToProfessorCommand implements Converter<Professor, ProfessorCommand> {

    private final CourseToCourseCommand courseToCourseCommand;

    public ProfessorToProfessorCommand(CourseToCourseCommand courseToCourseCommand) {
        this.courseToCourseCommand = courseToCourseCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public ProfessorCommand convert(Professor source) {
        if (source == null) {
            return null;
        }
        final ProfessorCommand professorCommand = new ProfessorCommand();
        professorCommand.setId(source.getId());
        professorCommand.setFirstName(source.getFirstName());
        professorCommand.setLastName(source.getLastName());
        professorCommand.setBio(source.getBio());
        professorCommand.setImage(source.getImage());

        if (source.getCourses() != null && source.getCourses().size() > 0){
            source.getCourses()
                    .forEach(course -> professorCommand.getCourses().add(courseToCourseCommand.convert(course)));
        }
        return professorCommand;
    }
}
