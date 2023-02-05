package com.kgt.university.controller;


import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.services.ProfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class ProfessorController {
    private ProfService profService;

    public ProfessorController(ProfService profService) {
        this.profService = profService;
    }

    @RequestMapping("/professors")
    public String getProfessors(Model model){
        model.addAttribute("professors", profService.getProfessors());
        return "professors";
    }
    @RequestMapping("professordetail/{profId}")
    public String viewProfessor(Model model, @PathVariable String profId){
        model.addAttribute("professor", profService.findProfessorById(Long.valueOf(profId)));
        return "professordetail";
    }
    @RequestMapping("/newsprofessorform")
    public String addProfessor(Model model){
        model.addAttribute("professor",new Professor());
        return "professorform";
    }
    @RequestMapping("professordetail/{profIdd}/update")
    public String updateProfessor(Model model, @PathVariable String profIdd){
        model.addAttribute("professor", profService.findProfessorById(Long.valueOf(profIdd)));
        return "professorform";
    }
/*    @GetMapping("course/{professorId}/new")
    public String newCourse(@PathVariable String professorId, Model model){
        Professor professor=profService.findProfessorById(Long.valueOf(professorId));
        Course course=new Course();
        professor.setId(Long.valueOf(professorId));
        model.addAttribute("course",course);
        return "courseform";
    }*/

 /*   @GetMapping("professor/{professorId}/course/{id}/update")
    public String updateProfessorCourse(@PathVariable String professorId,
                                        @PathVariable String id,Model model){
        model.addAttribute("course",courseService.findByProfessorAndCourseId(Long.valueOf(professorId),
                Long.valueOf(id)));
        return "courseform";
    }*/
    @PostMapping("professor/save")
    public String sOrU(@ModelAttribute Professor command){
        Professor savedCommand=profService.saveProfessor(command);
        System.out.println(savedCommand.getCourses());
        return "redirect:/professors";
    }
    @GetMapping("professordetail/{profIdd}/delete")
    public String deleteProfessorById(@PathVariable String profIdd){
        log.debug("Deleting id: " + profIdd);
        profService.deleteProfessorById(Long.valueOf(profIdd));
        System.out.println("Deleting"+profIdd);
        return "redirect:/professors";
    }


}
