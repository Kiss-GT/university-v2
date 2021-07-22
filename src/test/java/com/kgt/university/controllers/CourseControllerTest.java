package com.kgt.university.controllers;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.controller.CourseController;
import com.kgt.university.services.CourseService;
import com.kgt.university.services.ProfService;
import com.kgt.university.services.UniversityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourseControllerTest {

    @Mock
    CourseService courseService;

    @Mock
    private ProfService profService;




    CourseController controller;


    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new CourseController(courseService,profService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        ProfessorCommand professorCommand = new ProfessorCommand();
        when(profService.findProfessorCommandById(anyLong())).thenReturn(professorCommand);

        //when
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("courses"))
                .andExpect(model().attributeExists("courses"));

        //then
        verify(profService, times(1)).findProfessorCommandById(anyLong());
    }

    @Test
    public void testShowIngredient() throws Exception {
        //given
        CourseCommand courseCommand = new CourseCommand();

        //when
        when(courseService.findByProfessorAndCourseId(anyLong(), anyLong())).thenReturn(courseCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        ProfessorCommand professorCommand = new ProfessorCommand();
        professorCommand.setId(1L);

        //when
        when(profService.findProfessorCommandById(anyLong())).thenReturn(professorCommand);


        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(profService, times(1)).findProfessorCommandById(anyLong());

    }

    @Test
    public void testUpdateIngredientForm() throws Exception {
        //given
        CourseCommand courseCommand = new CourseCommand();

        //when
        when(courseService.findByProfessorAndCourseId(anyLong(), anyLong())).thenReturn(courseCommand);


        //then
        mockMvc.perform(get("/professor/1/course/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //given
        CourseCommand command = new CourseCommand();
        command.setId(3L);
        command.setProfessorId(2L);

        //when
        when(courseService.saveCourseCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/professor/2/coursenew")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("name", "course name")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/professordetail/{professorId}"));

    }

    @Test
    public void testDeleteIngredient() throws Exception {

        //then
        mockMvc.perform(get("/recipe/2/ingredient/3/delete")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(courseService, times(1)).deleteCourseById(anyLong(), anyLong());

    }
}