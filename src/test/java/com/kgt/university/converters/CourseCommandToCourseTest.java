package com.kgt.university.converters;


import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.StudentCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CourseCommandToCourseTest {


    public static final String NAME = "Hamburger";
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = new Long(1L);
    public static final Long STUDENT_ID_1 = 1L;
    public static final Long STUDENT_ID_2 = 2L;
    public static final Long PROF_ID_1 = 1L;

    CourseCommandToCourse converter;

    @Before
    public void setUp() throws Exception {
        converter = new CourseCommandToCourse(new StudentCommandToStudent());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CourseCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        CourseCommand command = new CourseCommand();
        command.setId(ID_VALUE);
        command.setName(NAME);
        command.setDescription(DESCRIPTION);

        StudentCommand student1 = new StudentCommand();
        student1.setId(STUDENT_ID_1);

        StudentCommand student2 = new StudentCommand();
        student2.setId(STUDENT_ID_2);

        Professor professor=new Professor();
        professor.setId(PROF_ID_1);

        command.getStudentCommands().add(student1);
        command.getStudentCommands().add(student2);

        //when
        Course course = converter.convert(command);

        //then
        assertNotNull(course);
        assertEquals(NAME, course.getName());
        assertEquals(DESCRIPTION, course.getDescription());
        assertEquals(ID_VALUE, course.getId());


        assertEquals(2, course.getStudents().size());
    }



}
