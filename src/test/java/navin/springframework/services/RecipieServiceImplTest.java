package navin.springframework.services;

import navin.springframework.domain.Recipie;
import navin.springframework.repositories.RecipieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipieServiceImplTest {

    RecipieServiceImpl recipieService;

    @Mock
    RecipieRepository recipieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipieService = new RecipieServiceImpl(recipieRepository);
    }

    @Test
    void getRecipies() {

        Recipie recipie = new Recipie();

        HashSet recipeData = new HashSet();
        recipeData.add(recipie);

        when(recipieRepository.findAll()).thenReturn(recipeData);

        Set<Recipie> recipies = recipieService.getRecipies();

        assertEquals(recipies.size(), 1);
        verify(recipieRepository, times(1)).findAll();

    }
}