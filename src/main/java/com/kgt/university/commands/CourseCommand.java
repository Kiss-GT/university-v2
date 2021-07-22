package com.kgt.university.commands;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class CourseCommand {

    private Long id;
    private Long professorId;
    private String name;
    private String description;
    private Set<StudentCommand> studentCommands=new HashSet<>();;




}
