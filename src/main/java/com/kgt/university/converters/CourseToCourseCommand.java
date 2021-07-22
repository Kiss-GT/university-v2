package com.kgt.university.converters;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Student;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
public class CourseToCourseCommand implements Converter<Course, CourseCommand> {

    private final StudentToStudentCommand studentToStudentCommand;

    public CourseToCourseCommand(StudentToStudentCommand studentToStudentCommand) {
        this.studentToStudentCommand = studentToStudentCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public CourseCommand convert(Course course) {
        if (course == null) {
            return null;
        }
        final CourseCommand courseCommand = new CourseCommand();
        courseCommand.setId(course.getId());
        courseCommand.setName(course.getName());
        courseCommand.setDescription(course.getDescription());

        if (course.getProfessor() != null) {
            courseCommand.setProfessorId(course.getProfessor().getId());
        }


        if (course.getStudents() != null && course.getStudents().size() > 0){
            course.getStudents()
                    .forEach((Student student) -> courseCommand.getStudentCommands().add(studentToStudentCommand.convert(student)));
        }

        return courseCommand;
    }
}
