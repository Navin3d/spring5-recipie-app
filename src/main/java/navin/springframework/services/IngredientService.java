package navin.springframework.services;

import navin.springframework.commands.IngredientsCommand;

public interface IngredientService {
    IngredientsCommand findByRecipieIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientsCommand saveIngredientCommand(IngredientsCommand ingredientsCommand);
    void deleteById(Long recipieId, Long ingredientId);
}
