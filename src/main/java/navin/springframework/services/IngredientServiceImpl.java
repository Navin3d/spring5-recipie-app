package navin.springframework.services;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.commands.IngredientsCommand;
import navin.springframework.converters.IngredientsCommandToIngredients;
import navin.springframework.converters.IngredientsToIngredientsCommand;
import navin.springframework.domain.Ingredients;
import navin.springframework.domain.Recipie;
import navin.springframework.repositories.RecipieRepository;
import navin.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Slf4j
@Controller
public class IngredientServiceImpl implements IngredientService{

    private final IngredientsCommandToIngredients ingredientsCommandToIngredients;
    private final IngredientsToIngredientsCommand ingredientsToIngredientsCommand;
    private final RecipieRepository recipieRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientsCommandToIngredients ingredientsCommandToIngredients, IngredientsToIngredientsCommand ingredientsToIngredientsCommand, RecipieRepository recipieRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientsCommandToIngredients = ingredientsCommandToIngredients;
        this.ingredientsToIngredientsCommand = ingredientsToIngredientsCommand;
        this.recipieRepository = recipieRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientsCommand findByRecipieIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipie> recipieOptional = recipieRepository.findById(Long.valueOf(recipeId));

        if(!recipieOptional.isPresent()){
            log.error("The recipie with id " + recipeId + "Not found...");
        }

        Recipie recipie = recipieOptional.get();

        Optional<IngredientsCommand> ingredientsCommandOptional = recipie.getIngredients().stream()
                .filter(ingredients -> ingredients.getId().equals(ingredientId))
                .map(ingredients -> ingredientsToIngredientsCommand.convert(ingredients)).findFirst();

        if(!ingredientsCommandOptional.isPresent()){
            log.error("Ingredient with id " + ingredientId + " Not found");
        }

        return ingredientsCommandOptional.get();
    }

    @Override
    public IngredientsCommand saveIngredientCommand(IngredientsCommand ingredientsCommand) {
        Optional<Recipie> optionalRecipie = recipieRepository.findById(ingredientsCommand.getRecipieId());

        if(!optionalRecipie.isPresent()) {
            return new IngredientsCommand();
        } else {
            Recipie recipie = optionalRecipie.get();
            Optional<Ingredients> ingredientsOptional = recipie
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientsCommand.getId()))
                    .findFirst();
            if(ingredientsOptional.isPresent()){
                Ingredients ingredientFound = ingredientsOptional.get();
                ingredientFound.setDescription(ingredientsCommand.getDescription());
                ingredientFound.setAmount(ingredientsCommand.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(ingredientsCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM not found...")));
            } else {
                recipie.addIngredients(ingredientsCommandToIngredients.convert(ingredientsCommand));
            }

            Recipie savedRecipie = recipieRepository.save(recipie);

            Optional<Ingredients> savedIngredientOptional = savedRecipie.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientsCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipie.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientsCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientsCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(ingredientsCommand.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientsToIngredientsCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(Long recipieId, Long ingredientId) {
        Optional<Recipie> recipieOptional = recipieRepository.findById(Long.valueOf(recipieId));

        if(recipieOptional.isPresent()) {

            Recipie recipie = recipieOptional.get();

            Optional<Ingredients> ingredientsOptional = recipie.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if(ingredientsOptional.isPresent()) {
                Ingredients ingredientToDelete = ingredientsOptional.get();
                ingredientToDelete.setRecipie(null);
                recipie.getIngredients().remove(ingredientToDelete);
                recipieRepository.save(recipie);
            }
        } else {
            log.error("The ingredient not found...");
        }

    }
}
