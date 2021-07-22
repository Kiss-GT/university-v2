package com.kgt.university.converters;

import com.kgt.university.commands.StudentCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Student;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



@Component
public class StudentToStudentCommand implements Converter<Student, StudentCommand> {

   /* private final CourseToCourseCommand courseToCourseCommand;

    public StudentToStudentCommand(CourseToCourseCommand courseToCourseCommand) {
        this.courseToCourseCommand = courseToCourseCommand;
    }*/


    @Synchronized
    @Nullable
    @Override
    public StudentCommand convert(Student source) {
        if (source == null) {
            return null;
        }
        final StudentCommand studentCommand = new StudentCommand();
        studentCommand.setId(source.getId());
        studentCommand.setFirstName(source.getFirstName());
        studentCommand.setLastName(source.getLastName());

       /* if (source.getCourses() != null && source.getCourses().size() > 0){
            source.getCourses()
                    .forEach((Course course) -> studentCommand.getCourses().add(courseToCourseCommand.convert(course)));
        }*/

        return studentCommand;
    }
}
