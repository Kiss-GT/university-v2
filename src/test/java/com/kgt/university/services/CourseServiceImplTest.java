package com.kgt.university.services;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.converters.*;
import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.repositories.CourseRepo;
import com.kgt.university.repositories.ProfRepo;
import com.kgt.university.repositories.StudRepo;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


public class CourseServiceImplTest {

    @Mock
    CourseRepo courseRepo;

    private final CourseToCourseCommand courseToCourseCommand;
    private final CourseCommandToCourse courseCommandToCourse;


   @Mock
   ProfRepo profRepo;

   @Mock
   StudRepo studRepo;

    private CourseService courseService;

    //init converters
    public CourseServiceImplTest() {
        this.courseToCourseCommand = new CourseToCourseCommand(new StudentToStudentCommand());
        this.courseCommandToCourse = new CourseCommandToCourse(new StudentCommandToStudent());

    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        courseService=new CourseServiceImpl(courseRepo,profRepo,courseCommandToCourse,courseToCourseCommand);



    }

    @Test
    public void findByRecipeIdAndId() throws Exception {
    }

    @Test
    public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
        //given
        Professor professor=new Professor();
        professor.setId(1L);


        Course course1 = new Course();
        course1.setId(1L);

        Course course2 = new Course();
        course2.setId(2L);

        Course course3 = new Course();
        course3.setId(3L);



        professor.addCourse(course1);
        professor.addCourse(course2);
        professor.addCourse(course3);
        Optional<Professor> professorOptional = Optional.of(professor);

        when(profRepo.findById(anyLong())).thenReturn(professorOptional);

        //then
        CourseCommand courseCommand = courseService.findByProfessorAndCourseId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), courseCommand.getId());
        assertEquals(Long.valueOf(1L), courseCommand.getProfessorId());
        verify(profRepo, times(1)).findById(anyLong());
    }


    @Test
    public void testSaveProfessorCommand() throws Exception {
        //given
        CourseCommand command = new CourseCommand();
        command.setId(3L);
        command.setProfessorId(3L);

        Optional<Professor> professorOptional = Optional.of(new Professor());

        Professor savedProfessor = new Professor();
        savedProfessor.addCourse(new Course());
        savedProfessor.getCourses().iterator().next().setId(3L);

        when(profRepo.findById(anyLong())).thenReturn(professorOptional);
        when(profRepo.save(any())).thenReturn(savedProfessor);

        //when
        CourseCommand savedCommand = courseService.saveCourseCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(profRepo, times(1)).findById(anyLong());
        verify(profRepo, times(1)).save(any(Professor.class));

    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        Professor professor = new Professor();
        Course course = new Course();
        course.setId(3L);
        professor.addCourse(course);
        course.setProfessor(professor);
        Optional<Professor> professorOptional = Optional.of(professor);

        when(profRepo.findById(anyLong())).thenReturn(professorOptional);

        //when
        courseService.deleteCourseById(1L, 3L);

        //then
        verify(profRepo, times(1)).findById(anyLong());
        verify(profRepo, times(1)).save(any(Professor.class));
    }
}