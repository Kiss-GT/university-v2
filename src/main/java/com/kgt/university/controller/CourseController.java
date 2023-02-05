package com.kgt.university.controller;


import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.services.CourseService;
import com.kgt.university.services.ProfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/newscourseform")
    public String addCourse(Model model){

        List<Professor> listProfessors = courseService.listProfessors();
        model.addAttribute("course",new Course());
        model.addAttribute("listProfessors", listProfessors);
        return "courseform";
    }
@GetMapping("/course/{id}/update")
    public String updateCourse(@PathVariable String id,Model model){

        List<Professor> listProfessors = courseService.listProfessors();
        model.addAttribute("course",courseService.findCourseById(Long.valueOf(id)));
        model.addAttribute("listProfessors", listProfessors);
        return "courseform";
    }


    @PostMapping("course/save")
    public String saveOrUpdate(@ModelAttribute Course course){


        courseService.saveCourse(course);

        System.out.println("Saving "+course.getName());
        System.out.println("Saving "+course.getDescription());
        return "redirect:/courses";
    }

    @GetMapping("course/{courseId}/delete")
    public String deleteCourseById(@PathVariable String courseId){
        courseService.deleteCourseById( Long.valueOf(courseId));
        System.out.println("Deleting"+courseId);
        return "redirect:/courses";
    }



}
