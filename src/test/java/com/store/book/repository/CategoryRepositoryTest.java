package com.store.book.repository;

import com.store.book.model.Category;
import com.store.book.repository.category.CategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find category by ids when price ids are 1 and 2")
    public void findCategoryByIds_CategoriesSizeTwo_() {
        Category history = new Category();
        history.setId(1L);
        history.setName("history");
        history.setDescription("Historical books");
        categoryRepository.save(history);
        Category biography = new Category();
        biography.setId(2L);
        biography.setName("biography");
        biography.setDescription("Biography books");
        categoryRepository.save(history);
    }
}
