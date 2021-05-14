package navin.springframework.bootstrap;

import lombok.extern.slf4j.Slf4j;
import navin.springframework.domain.*;
import navin.springframework.repositories.CategoryRepository;
import navin.springframework.repositories.RecipieRepository;
import navin.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipieBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private RecipieRepository recipieRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipieBootstrap(RecipieRepository recipieRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipieRepository = recipieRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipie> getRecipies() {

        List<Recipie> recipies = new ArrayList<>(2);

        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");
        if (!eachUomOptional.isPresent()) {
            throw new RuntimeException("Expected Each Uom Not found...");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("TableSpoon");
        if (!tableSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected TableSpoon Uom Not Found...");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("TeaSpoon");
        if (!teaSpoonUomOptional.isPresent()) {
            throw new RuntimeException("Expected Tea Spoon Uom Not Found...");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");
        if (!dashUomOptional.isPresent()) {
            throw new RuntimeException("Expected Dash Uom Not Found...");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");
        if (!pintUomOptional.isPresent()) {
            throw new RuntimeException("Expected Pint Uom Not Found...");
        }

        Optional<UnitOfMeasure> cupUomOptional = unitOfMeasureRepository.findByDescription("Cup");
        if (!cupUomOptional.isPresent()) {
            throw new RuntimeException("Expected Cup Uom Not Found...");
        }

        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaSpoonUom = teaSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupUom = cupUomOptional.get();

        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if(!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found...");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if(!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected Category Not Found...");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        Recipie guacRecipie = new Recipie();
        guacRecipie.setDescription("Perfect Quac");
        guacRecipie.setCookTime(0);
        guacRecipie.setPrepTime(10);
        guacRecipie.setDifficulty(Difficulty.EASY);
        guacRecipie.setDirections("This is the directions to do guac Recipie...");

        Notes guacNotes = new Notes();
        guacNotes.setRecipieNotes("This is the notes of guac recipie.");
        guacRecipie.setNotes(guacNotes);

        guacRecipie.addIngredients(new Ingredients("ripe avacados", new BigDecimal(2), eachUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("kosher salt", new BigDecimal(5), teaSpoonUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("minced red onion or thinly sliced greeen onion", new BigDecimal(2), tableSpoonUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("serrano chiles stem and seeds removed minced", new BigDecimal(2), eachUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("silantro", new BigDecimal(2), tableSpoonUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("treshly grained black pepper", new BigDecimal(2), dashUom, guacRecipie));
        guacRecipie.addIngredients(new Ingredients("ripe tomatoes seeds and pulp removed", new BigDecimal(5), eachUom, guacRecipie));

        guacRecipie.getCategory().add(americanCategory);
        guacRecipie.getCategory().add(mexicanCategory);

        recipies.add(guacRecipie);

        Recipie tacosRecipie = new Recipie();
        tacosRecipie.setDescription("Spicy grilles chicken tacos");
        tacosRecipie.setPrepTime(20);
        tacosRecipie.setCookTime(9);
        tacosRecipie.setDifficulty(Difficulty.MODERATE);
        tacosRecipie.setDirections("This is the directions for tacos recipipe");

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipieNotes("This is the notes of tacos notes");
        tacosRecipie.setNotes(tacosNotes);

        tacosRecipie.addIngredients(new Ingredients("Aachi Chilli Powder.", new BigDecimal(2), tableSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Dried Oregano", new BigDecimal(1), teaSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Dried Cumin", new BigDecimal(1), teaSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("sugar", new BigDecimal(1), teaSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("salt", new BigDecimal(".5"), teaSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Clove of Garlic chopped", new BigDecimal(1), eachUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Finely grated orange zest.", new BigDecimal(1), tableSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("fresh-squeeced orange juice.", new BigDecimal(3), tableSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Olive oil.", new BigDecimal(2), tableSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Boneless chiced thighs.", new BigDecimal(4), tableSpoonUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Small corn tortilas.", new BigDecimal(8), eachUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Packed baby aruguls", new BigDecimal(3), cupUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Medium riped avacado sice", new BigDecimal(2), eachUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Radishes thinly sliced.", new BigDecimal(4), eachUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Cherry tomaos halved.", new BigDecimal(".5"), pintUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Red onion thinly sliced.", new BigDecimal(".2"), eachUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Roughly chopped cilantro.", new BigDecimal(4), eachUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Cup sour cream thinning with 1/4 cup milk", new BigDecimal(4), cupUom, tacosRecipie));
        tacosRecipie.addIngredients(new Ingredients("Lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipie));

        tacosRecipie.getCategory().add(americanCategory);
        tacosRecipie.getCategory().add(mexicanCategory);

        recipies.add(tacosRecipie);

        return recipies;

    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("I'm inside recipieBootstrap getRecipie...");
        recipieRepository.saveAll(getRecipies());
    }
}
