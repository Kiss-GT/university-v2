package com.kgt.university.converters;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.domain.Student;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CourseToCourseCommandTest {
    public static final Professor PROFESSOR=new Professor();
    public static final String NAME = "Hamburger";
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = new Long(1L);
    public static final Long STUDENT_ID_1 = 1L;
    public static final Long STUDENT_ID_2 = 2L;
    public static final Long PROF_ID_1 = 1L;


    CourseToCourseCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new CourseToCourseCommand(new StudentToStudentCommand());
    }

    @Test
    public void testNullConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new Course()));
    }


    @Test
    public void testConvertWithUom() throws Exception {
        //given
        Course course = new Course();
        course.setId(ID_VALUE);
        course.setProfessor(PROFESSOR);
        course.setName(NAME);
        course.setDescription(DESCRIPTION);

        Student student1=new Student();
        student1.setId(STUDENT_ID_1);

        Student student2=new Student();
        student2.setId(STUDENT_ID_2);

        course.getStudents().add(student1);
        course.getStudents().add(student2);

        //when
        CourseCommand courseCommand = converter.convert(course);
        //then
        assertEquals(ID_VALUE, courseCommand.getId());
        assertEquals(NAME, courseCommand.getName());
        assertEquals(DESCRIPTION, courseCommand.getDescription());
    }
}