package com.kgt.university.commands;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class StudentCommand {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<CourseCommand> courses=new HashSet<>();;

}

