package navin.springframework.services;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.commands.RecipieCommand;
import navin.springframework.converters.RecipieCommandToRecipie;
import navin.springframework.converters.RecipieToRecipieCommand;
import navin.springframework.domain.Recipie;
import navin.springframework.exceptions.NotFoundException;
import navin.springframework.repositories.RecipieRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipieServiceImpl implements RecipieService{

    private final RecipieRepository recipieRepository;
    private final RecipieToRecipieCommand recipieToRecipieCommand;
    private final RecipieCommandToRecipie recipieCommandToRecipie;

    public RecipieServiceImpl(RecipieRepository recipieRepository, RecipieToRecipieCommand recipieToRecipieCommand, RecipieCommandToRecipie recipieCommandToRecipie) {
        this.recipieRepository = recipieRepository;
        this.recipieToRecipieCommand = recipieToRecipieCommand;
        this.recipieCommandToRecipie = recipieCommandToRecipie;
    }


    @Override
    public Set<Recipie> getRecipies() {
        log.debug("I'm the service...");

        Set<Recipie> recipieSet = new HashSet<>();
        recipieRepository.findAll().iterator().forEachRemaining(recipieSet::add);
        return recipieSet;
    }

    @Override
    public Recipie findById(Long l) {
        Optional<Recipie> recipieOptional = recipieRepository.findById(l);

        if(!recipieOptional.isPresent()) {
            throw  new NotFoundException("Recpie with id " + l.toString() + "Not Found...");
        }

        return recipieOptional.get();
    }

    @Override
    @Transactional
    public RecipieCommand findCommandById(Long l) {
        return recipieToRecipieCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public RecipieCommand saveRecipieCommand(RecipieCommand recipieCommand) {
        Recipie detachedRecipie = recipieCommandToRecipie.convert(recipieCommand);
        Recipie savedRecipie = recipieRepository.save(detachedRecipie);
        return recipieToRecipieCommand.convert(savedRecipie);
    }

    @Override
    @Transactional
    public void deleteById(Long idToDelete) {
        recipieRepository.deleteById(idToDelete);
    }

}
