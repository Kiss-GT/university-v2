package com.kgt.university.converters;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandToCourse implements Converter<CourseCommand, Course> {

    private final StudentCommandToStudent studentCommandToStudent;

 public CourseCommandToCourse(StudentCommandToStudent studentCommandToStudent) {
     this.studentCommandToStudent = studentCommandToStudent;
 }


    @Nullable
    @Override
    public Course convert(CourseCommand source) {
        if(source == null) {
            return null;
        }

        final Course course = new Course();
        course.setId(source.getId());
        if (source.getProfessorId()!=null){
            Professor professor=new Professor();
            professor.setId(source.getProfessorId());
            course.setProfessor(professor);
            professor.addCourse(course);
        }
        course.setName(source.getName());
        course.setDescription(source.getDescription());


        if (source.getStudentCommands() != null && source.getStudentCommands().size() > 0){
            source.getStudentCommands()
                    .forEach( student -> course.getStudents().add(studentCommandToStudent.convert(student)));
        }

        return course;
    }
}
