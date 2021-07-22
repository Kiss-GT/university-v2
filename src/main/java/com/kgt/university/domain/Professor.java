package com.kgt.university.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude = {"courses"})

public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String firstName;

    private String lastName;

    @Lob
    private String bio;

    @Lob
    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy ="professor")
    private Set<Course> courses=new HashSet<>();


    public Professor addCourse(Course course){
        course.setProfessor(this);
        this.courses.add(course);
        return this;
    }
}
