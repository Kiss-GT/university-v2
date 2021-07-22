package com.kgt.university.converters;

import com.kgt.university.commands.StudentCommand;
import com.kgt.university.domain.Student;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StudentCommandToStudent implements Converter<StudentCommand, Student> {

    /*private final CourseCommandToCourse courseCommandToCourse;

    public StudentCommandToStudent(CourseCommandToCourse courseCommandToCourse) {
        this.courseCommandToCourse = courseCommandToCourse;
    }*/

    @Synchronized
    @Nullable
    @Override
    public Student convert(StudentCommand source) {
        if(source == null) {
            return null;
        }

        final Student student = new Student();
        student.setId(source.getId());
        student.setFirstName(source.getFirstName());
        student.setLastName(source.getLastName());

       /* if (source.getCourses() != null && source.getCourses().size() > 0){
            source.getCourses()
                    .forEach( course -> student.getCourses().add(courseCommandToCourse.convert(course)));
        }*/

        return student;
    }
}
