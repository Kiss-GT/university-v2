package com.kgt.university.domain;


import lombok.*;

import javax.persistence.*;

import java.util.HashSet;

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
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students=new HashSet<>();

    @ManyToOne
    private Professor professor;

    public Course(String name, String description, Professor professor) {
        this.name = name;
        this.description = description;
        this.professor = professor;
    }
}
