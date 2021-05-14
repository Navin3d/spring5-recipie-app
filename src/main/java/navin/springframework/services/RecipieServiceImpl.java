package navin.springframework.services;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.domain.Recipie;
import navin.springframework.repositories.RecipieRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipieServiceImpl implements RecipieService{

    private final RecipieRepository recipieRepository;

    public RecipieServiceImpl(RecipieRepository recipieRepository) {
        this.recipieRepository = recipieRepository;
    }


    @Override
    public Set<Recipie> getRecipies() {
        log.debug("I'm the service...");

        Set<Recipie> recipieSet = new HashSet<>();
        recipieRepository.findAll().iterator().forEachRemaining(recipieSet::add);
        return recipieSet;
    }

}
