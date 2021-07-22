package com.kgt.university.domain;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"students"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    public Set<Student> students=new HashSet<>();;

    @ManyToOne
    private Professor professor;

    public Course(String name, String description, Professor professor) {
        this.name = name;
        this.description = description;
        this.professor = professor;
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(student);
        //student.setCourse(this);
    }


}
