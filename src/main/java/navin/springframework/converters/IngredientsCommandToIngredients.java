package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.IngredientsCommand;
import navin.springframework.domain.Ingredients;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientsCommandToIngredients implements Converter<IngredientsCommand, Ingredients> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientsCommandToIngredients(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredients convert(IngredientsCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredients ingredient = new Ingredients();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}
