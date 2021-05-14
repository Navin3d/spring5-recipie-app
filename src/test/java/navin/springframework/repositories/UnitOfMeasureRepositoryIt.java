package navin.springframework.repositories;

import navin.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryIt {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DirtiesContext
    void findByDescription() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("TeaSpoon");
        assertEquals("TeaSpoon", uomOptional.get().getDescription());
    }

    @Test
    void findByDescriptionCup() {
        Optional<UnitOfMeasure> optionalUnitOfMeasure = unitOfMeasureRepository.findByDescription("Cup");
        assertEquals("Cup", optionalUnitOfMeasure.get().getDescription());
    }
}