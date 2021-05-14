package navin.springframework.controllers;

import navin.springframework.domain.Recipie;
import navin.springframework.services.RecipieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class IndexControllerTest {

    @Mock
    RecipieService recipieService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipieService);
    }

    @Test
    void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {

        Set<Recipie> recipies = new HashSet<>();
        recipies.add(new Recipie());

        Recipie recipie = new Recipie();
        recipie.setId(1L);
        recipies.add(recipie);

        when(recipieService.getRecipies()).thenReturn(recipies);
        ArgumentCaptor<Set<Recipie>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String viewName = indexController.getIndexPage(model);


        //then
        assertEquals("index", viewName);
        verify(recipieService, times(1)).getRecipies();
        verify(model, times(1)).addAttribute(eq("recipies"), argumentCaptor.capture());
        Set<Recipie> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}