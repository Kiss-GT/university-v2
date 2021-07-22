package com.kgt.university.controller;

import com.kgt.university.commands.CourseCommand;
import com.kgt.university.commands.StudentCommand;
import com.kgt.university.services.CourseService;
import com.kgt.university.services.StudentService;
import com.kgt.university.services.UniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;


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
        model.addAttribute("student",new StudentCommand());
        return "studentform";
    }
    @PostMapping
    @RequestMapping("student")
    public String sOrU(@ModelAttribute StudentCommand command){
        StudentCommand savedCommand=studentService.saveStudentCommand(command);


        return "redirect:/students";
    }
    @RequestMapping("studentdetail/{id}/update")
    public String updateStudent(@PathVariable String id, Model model, StudentCommand command){
        model.addAttribute("student", studentService.findStudentById(Long.valueOf(id)));
        model.addAttribute("studentcourses", courseService.getCourses());
       return "studentdetail";
    }
    @GetMapping("studentdetail/{id}/delete")
    public String deleteById(@PathVariable String id){
       log.debug("Deleting id: " + id);
        studentService.deleteStudentById(Long.valueOf(id));
        System.out.println("Deleting"+id);
        return "redirect:/students";
    }
    @GetMapping("studentdetail/{studentId}/new")
    public String newCourseToStudent(@PathVariable String studentId, Model model){

      StudentCommand studentCommand= studentService.findStudentCommandById(Long.valueOf(studentId));


        CourseCommand courseCommand=new CourseCommand();
        //courseCommand.setStudentCommands(Long.valueOf(studentId));
        model.addAttribute("course",courseCommand);
        return "courseforstudentform";
    }
    @PostMapping("/student/{studentId}/save")
    public String saveCourseToStudent(@ModelAttribute StudentCommand command){
        StudentCommand savedCommand=studentService.saveStudentCommand(command);
       return "redirect:/studentdetail/{studentId}/update";
    }


}
