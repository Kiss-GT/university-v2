package com.kgt.university.controller;


import com.kgt.university.domain.Course;


import com.kgt.university.domain.Student;
import com.kgt.university.services.CourseService;
import com.kgt.university.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;


@Slf4j
@Controller
public class StudentController {

    private StudentService studentService;
    private CourseService courseService;

    public StudentController(StudentService studentService,CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @RequestMapping("/students")
    public String getStudents(Model model){
        model.addAttribute("students", studentService.getStudents());
        return "students";
    }
    @RequestMapping("/newstudentform")
    public String addStudents(Model model){

        List<Course> listCourses=courseService.getCourses();
        model.addAttribute("student",new Student());
        model.addAttribute("listCourses",listCourses);
        return "editstudentform";
    }
  /*  @RequestMapping("studentdetail/{id}/view")
    public String viewStudent(@PathVariable String id,Model model){

        model.addAttribute("student", studentService.findStudentById(Long.valueOf(id)));

        return "studentdetail";
    }*/

    @RequestMapping("studentdetail/{id}/update")
    public String updateStudent(@PathVariable String id, Model model, Student command,RedirectAttributes redirectAttributes){
        List<Course> listCourses=courseService.getCourses();
        model.addAttribute("student", studentService.findStudentById(Long.valueOf(id)));
        model.addAttribute("studentcourses", courseService.getCourses());
        model.addAttribute("listCourses",listCourses);
        redirectAttributes.addFlashAttribute("message","The student has been saved successfully.");
       return "editstudentform";
    }
    @GetMapping("studentdetail/{id}/delete")
    public String deleteById(@PathVariable String id){
       log.debug("Deleting id: " + id);
        studentService.deleteStudentById(Long.valueOf(id));
        System.out.println("Deleting"+id);
        return "redirect:/students";
    }

    @PostMapping("/student/{studentId}/save")
    public String saveCourseToStudent(Student command, RedirectAttributes redirectAttributes){
        studentService.saveStudent(command);
        redirectAttributes.addFlashAttribute("message", "The student has been saved successfully.");
        return "redirect:/students";
    }



}
