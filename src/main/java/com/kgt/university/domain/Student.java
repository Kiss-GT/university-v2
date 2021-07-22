package com.kgt.university.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"courses"})
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String firstName;

    private String lastName;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses=new HashSet<>();



    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }


}
