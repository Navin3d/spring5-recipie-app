package navin.springframework.services;

import navin.springframework.domain.Recipie;

import java.util.Set;

public interface RecipieService {
    Set<Recipie> getRecipies();
}
