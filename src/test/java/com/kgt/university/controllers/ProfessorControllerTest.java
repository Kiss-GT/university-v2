package com.kgt.university.controllers;


import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.controller.ProfessorController;
import com.kgt.university.domain.Professor;
import com.kgt.university.services.ProfService;
import com.kgt.university.services.UniversityService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jt on 6/19/17.
 */
public class ProfessorControllerTest {

    @Mock
    private ProfService profService;

    ProfessorController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new ProfessorController(profService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Professor professor = new Professor();
        professor.setId(1L);

        when(profService.findProfessorById(anyLong())).thenReturn(professor);

        mockMvc.perform(get("/professordetail/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("professordetail"))
                .andExpect(model().attributeExists("professor"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        ProfessorCommand command = new ProfessorCommand();

        mockMvc.perform(get("/newsprofessorform"))
                .andExpect(status().isOk())
                .andExpect(view().name("professorform"))
                .andExpect(model().attributeExists("professor"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        ProfessorCommand command = new ProfessorCommand();
        command.setId(2L);

        when(profService.saveProfessorCommand(any())).thenReturn(command);

        mockMvc.perform(post("/professor")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/professors"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        ProfessorCommand command = new ProfessorCommand();
        command.setId(2L);

        when(profService.findProfessorCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(profService, times(1)).deleteProfessorById(anyLong());
    }
}