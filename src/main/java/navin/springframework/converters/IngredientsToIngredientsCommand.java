package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.IngredientsCommand;
import navin.springframework.domain.Ingredients;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientsToIngredientsCommand implements Converter<Ingredients, IngredientsCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientsToIngredientsCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientsCommand convert(Ingredients ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientsCommand ingredientCommand = new IngredientsCommand();
        ingredientCommand.setId(ingredient.getId());
        if(ingredient.getRecipie() != null){
            ingredientCommand.setRecipieId(ingredient.getRecipie().getId());
        }
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUom(uomConverter.convert(ingredient.getUnitOfMeasure()));
        return ingredientCommand;
    }
}
