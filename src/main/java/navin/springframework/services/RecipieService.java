package navin.springframework.services;

import navin.springframework.commands.RecipieCommand;
import navin.springframework.domain.Recipie;

import java.util.Set;

public interface RecipieService {
    Set<Recipie> getRecipies();
    Recipie findById(Long l);
    RecipieCommand findCommandById(Long l);
    RecipieCommand saveRecipieCommand(RecipieCommand recipieCommand);
    void deleteById(Long l);
}
