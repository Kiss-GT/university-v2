package com.kgt.university.converters;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProfCommandToProfTest {
    public static final Long PROF_ID = 1L;
    public static final String FIRST_NAME = "My Recipe";
    public static final String LAST_NAME = "Directions";
    public static final String BIO = "Once upon a time";
    public static final Long COURSE_ID_1 = 3L;
    public static final Long COURSE_ID_2 = 4L;


    ProfessorCommandToProfessor converter;


    @Before
    public void setUp() throws Exception {
        converter = new ProfessorCommandToProfessor(new CourseCommandToCourse(new StudentCommandToStudent())) ;
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new ProfessorCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        ProfessorCommand professorCommand = new ProfessorCommand();
        professorCommand.setId(PROF_ID);
        professorCommand.setFirstName(FIRST_NAME);
        professorCommand.setLastName(LAST_NAME);
        professorCommand.setBio(BIO);

        CourseCommand course1=new CourseCommand();
        course1.setId(COURSE_ID_1);

        CourseCommand course2=new CourseCommand();
        course1.setId(COURSE_ID_2);

        professorCommand.getCourses().add(course1);
        professorCommand.getCourses().add(course2);



        //when
        Professor professor  = converter.convert(professorCommand);

        assertNotNull(professor);
        assertEquals(PROF_ID, professor.getId());
        assertEquals(FIRST_NAME, professor.getFirstName());
        assertEquals(LAST_NAME, professor.getLastName());
        assertEquals(BIO, professor.getBio());

        assertEquals(2, professor.getCourses().size());
    }

}