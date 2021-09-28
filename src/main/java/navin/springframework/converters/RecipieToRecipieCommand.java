package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.RecipieCommand;
import navin.springframework.domain.Category;
import navin.springframework.domain.Ingredients;
import navin.springframework.domain.Recipie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipieToRecipieCommand implements Converter<Recipie, RecipieCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientsToIngredientsCommand ingredientsConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipieToRecipieCommand(CategoryToCategoryCommand categoryConverter, IngredientsToIngredientsCommand ingredientsConverter, NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientsConverter = ingredientsConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipieCommand convert(Recipie source) {
        if(source == null)
            return null;
        final RecipieCommand recipieCommand = new RecipieCommand();
        recipieCommand.setId(source.getId());
        recipieCommand.setDescription(source.getDescription());
        recipieCommand.setPrepTime(source.getPrepTime());
        recipieCommand.setCookTime(source.getCookTime());
        recipieCommand.setServings(source.getServings());
        recipieCommand.setSource(source.getSource());
        recipieCommand.setDifficulty(source.getDifficulty());
        recipieCommand.setDirections(source.getDirections());
        recipieCommand.setUrl(source.getUrl());
        recipieCommand.setImage(source.getImage());
        recipieCommand.setNotes(notesConverter.convert(source.getNotes()));

        if(source.getCategory() != null && source.getCategory().size() > 0) {
            source.getCategory()
                    .forEach((Category category) -> recipieCommand.getCategory().add(categoryConverter.convert(category)));
        }

        if(source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach((Ingredients ingredients) -> recipieCommand.getIngredients().add(ingredientsConverter.convert(ingredients)));
        }

        return recipieCommand;

    }
}
