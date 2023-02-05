package com.kgt.university.services;


import com.kgt.university.domain.Course;
import com.kgt.university.domain.Professor;
import com.kgt.university.repositories.ProfRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class ProfService {

    private final ProfRepo profRepo;

    public ProfService(ProfRepo profRepo) {
        this.profRepo = profRepo;
    }

    public Professor findProfessorById(Long l) {
        Optional<Professor> detailsOptional=profRepo.findById(l);
        if(!detailsOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found");
        }
        return detailsOptional.get();
    }

    public Set<Professor> getProfessors() {
        Set<Professor>professorSet=new HashSet<>();
        profRepo.findAll().iterator().forEachRemaining(professorSet::add);
        return professorSet;
    }

    public Professor saveProfessor(Professor command) {
        Professor savedProfessor=profRepo.save(command);
        return savedProfessor;
    }

    public void deleteProfessorById(Long idToDelete) {
        profRepo.deleteById(idToDelete);
    }
}
