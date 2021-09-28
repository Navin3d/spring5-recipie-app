package navin.springframework.converters;

import com.sun.istack.Nullable;
import lombok.Synchronized;
import navin.springframework.commands.CategoryCommand;
import navin.springframework.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null)
            return null;

        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        //category.setRecipie(source.getRecipie());

        return category;
        
    }
}
