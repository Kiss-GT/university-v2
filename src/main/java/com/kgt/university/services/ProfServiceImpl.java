package com.kgt.university.services;

import com.kgt.university.commands.ProfessorCommand;
import com.kgt.university.converters.ProfessorCommandToProfessor;
import com.kgt.university.converters.ProfessorToProfessorCommand;
import com.kgt.university.domain.Professor;
import com.kgt.university.repositories.ProfRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProfServiceImpl implements ProfService{

    private final ProfRepo profRepo;
    private final ProfessorCommandToProfessor professorCommandToProfessor;
    private final ProfessorToProfessorCommand professorToProfessorCommand;

    public ProfServiceImpl(ProfRepo profRepo, ProfessorCommandToProfessor professorCommandToProfessor, ProfessorToProfessorCommand professorToProfessorCommand) {
        this.profRepo = profRepo;
        this.professorCommandToProfessor = professorCommandToProfessor;
        this.professorToProfessorCommand = professorToProfessorCommand;
    }

    @Override
    public Set<Professor> getProfessors() {
        Set<Professor>professorSet=new HashSet<>();
        profRepo.findAll().iterator().forEachRemaining(professorSet::add);
        return professorSet;
    }

    @Override
    public Professor findProfessorById(Long l) {
        Optional<Professor> detailsOptional=profRepo.findById(l);
        if(!detailsOptional.isPresent()){
            throw new RuntimeException("Recipe Not Found");
        }
        return detailsOptional.get();
    }

    @Override
    public ProfessorCommand saveProfessorCommand(ProfessorCommand command) {
        Professor detachedProfessor=professorCommandToProfessor.convert(command);
        Professor savedProfessor=profRepo.save(detachedProfessor);
        log.debug("Saved RecipeId:" + savedProfessor.getId());
        return professorToProfessorCommand.convert(savedProfessor);
    }

    @Override
    public ProfessorCommand findProfessorCommandById(Long l) {
        return professorToProfessorCommand.convert(findProfessorById(l));
    }

    @Override
    public void deleteProfessorById(Long idToDelete) {
        profRepo.deleteById(idToDelete);
    }
}
