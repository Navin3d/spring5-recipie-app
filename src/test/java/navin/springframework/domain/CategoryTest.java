package navin.springframework.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long intValue = 4l;
        category.setId(intValue);
        assertEquals(intValue, category.getId());
    }

    @Test
    void getDescription() {
    }

    @Test
    void getRecipie() {
    }
}