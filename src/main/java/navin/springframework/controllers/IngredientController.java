package navin.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.commands.IngredientsCommand;
import navin.springframework.commands.RecipieCommand;
import navin.springframework.commands.UnitOfMeasureCommand;
import navin.springframework.services.IngredientService;
import navin.springframework.services.RecipieService;
import navin.springframework.services.UnitOfMeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipieService recipieService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipieService recipieService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipieService = recipieService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipie/{recipieId}/ingredient")
    public String listIngredient(@PathVariable String recipieId, Model model) {
        model.addAttribute("recipie", recipieService.findCommandById(Long.valueOf(recipieId)));
        return "recipie/ingredient/list";
    }

    @GetMapping("recipie/{recipieId}/ingredient/new")
    public String newIngredient(@PathVariable String recipieId, Model model){

        RecipieCommand recipieCommand = recipieService.findCommandById(Long.valueOf(recipieId));

        IngredientsCommand ingredientsCommand = new IngredientsCommand();

        ingredientsCommand.setRecipieId(Long.valueOf(recipieId));

        model.addAttribute("ingredient", ingredientsCommand);

        ingredientsCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipie/ingredient/ingredientForm";
    }

    @GetMapping("recipie/{recipieId}/ingredient/{ingredientId}/show")
    public String showRecipieIngredient(@PathVariable String recipieId, @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipieIdAndIngredientId(Long.valueOf(recipieId), Long.valueOf(ingredientId)));
        return "recipie/ingredient/show";
    }

    @GetMapping("recipie/{recipieId}/ingredient/{ingredientId}/update")
    public String updateRecipieIngredient(@PathVariable String recipieId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipieIdAndIngredientId(Long.valueOf(recipieId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipie/ingredient/ingredientForm";
    }

    @PostMapping("recipie/{recipieId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientsCommand ingredientsCommand){
        IngredientsCommand savedCommand = ingredientService.saveIngredientCommand(ingredientsCommand);
        return "redirect:/recipie/" + ingredientsCommand.getRecipieId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipie/{recipieId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipieId, @PathVariable String ingredientId){
        ingredientService.deleteById(Long.valueOf(recipieId), Long.valueOf(ingredientId));
        return "redirect:/recipie/" + recipieId + "/ingredient";
    }
}
