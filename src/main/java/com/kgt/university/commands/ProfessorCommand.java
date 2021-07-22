package com.kgt.university.commands;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorCommand {


    private Long Id;
    private String firstName;
    private String lastName;
    private String bio;
    private Byte[] image;
    private Set<CourseCommand> courses= new HashSet<>();
}
