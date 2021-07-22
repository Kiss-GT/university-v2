package com.kgt.university.controller;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.commands.StudentCommand;
import com.kgt.university.repositories.CourseRepo;
import com.kgt.university.services.CourseService;
import com.kgt.university.services.ProfService;
import com.kgt.university.services.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class CourseController {

    private CourseService courseService;
    private ProfService profService;

    public CourseController(CourseService courseService,ProfService profService) {
        this.courseService = courseService;
        this.profService=profService;
    }

    @RequestMapping("/courses")
    public String getCourses(Model model){
        model.addAttribute("courses", courseService.getCourses());
        return "courses";
    }
    @GetMapping
    @RequestMapping("professor/{professorId}/course/{id}/update")
    public String updateProfessorCourse(@PathVariable String professorId,
                                         @PathVariable String id,Model model){
        model.addAttribute("course",courseService.findByProfessorAndCourseId(Long.valueOf(professorId),
                Long.valueOf(id)));
        return "courseform";
    }
    @GetMapping("course/{professorId}/new")
    public String newCourse(@PathVariable String professorId, Model model){
        ProfessorCommand professorCommand=profService.findProfessorCommandById(Long.valueOf(professorId));
        CourseCommand courseCommand=new CourseCommand();
        courseCommand.setProfessorId(Long.valueOf(professorId));
        model.addAttribute("course",courseCommand);
        return "courseform";
    }
    @PostMapping("professor/{professorId}/coursenew")
    public String saveOrUpdate(@ModelAttribute CourseCommand command){
        courseService.saveCourseCommand(command);

        System.out.println("Saving "+command.getName());
        System.out.println("Saving "+command.getDescription());
        return "redirect:/professordetail/{professorId}";
    }

}
