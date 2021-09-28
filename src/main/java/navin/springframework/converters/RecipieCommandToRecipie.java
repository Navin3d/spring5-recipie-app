package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.RecipieCommand;
import navin.springframework.domain.Recipie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipieCommandToRecipie implements Converter<RecipieCommand, Recipie> {

    private final CategoryCommandToCategory categoryConverter;
    private final IngredientsCommandToIngredients ingredientsConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipieCommandToRecipie(CategoryCommandToCategory categoryConverter, IngredientsCommandToIngredients ingredientsConverter, NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientsConverter = ingredientsConverter;
        this.notesConverter = notesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipie convert(RecipieCommand source) {
        if(source == null)
            return null;

        final Recipie recipie = new Recipie();
        recipie.setId(source.getId());
        recipie.setDescription(source.getDescription());
        recipie.setPrepTime(source.getPrepTime());
        recipie.setCookTime(source.getCookTime());
        recipie.setDifficulty(source.getDifficulty());
        recipie.setNotes(notesConverter.convert(source.getNotes()));
        recipie.setServings(source.getServings());
        recipie.setUrl(source.getUrl());
        recipie.setImage(source.getImage());
        recipie.setSource(source.getSource());

        if(source.getIngredients() != null && source.getIngredients().size() > 0)
            source.getIngredients()
                    .forEach(ingredient -> recipie.getIngredients().add(ingredientsConverter.convert(ingredient)));

        if (source.getCategory() != null && source.getCategory().size() > 0)
            source.getCategory()
                    .forEach(category -> recipie.getCategory().add(categoryConverter.convert(category)));

        return recipie;
    }
}
