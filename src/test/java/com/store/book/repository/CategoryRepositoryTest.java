package com.store.book.repository;

import com.store.book.model.Category;
import com.store.book.repository.category.CategoryRepository;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find category by ids when category id(s) are 1 and 2")
    void findByIdIn_CheckCategorySize_ReturnsSizeTwo() {
        Category history = new Category();
        history.setId(1L);
        history.setName("history");
        history.setDescription("Historical books");
        categoryRepository.save(history);
        Category biography = new Category();
        biography.setId(2L);
        biography.setName("biography");
        biography.setDescription("Biography books");
        categoryRepository.saveAll(List.of(history, biography));

        int expectedSize = 2;

        Set<Long> categoryIds = Set.of(history.getId(), biography.getId());
        Set<Category> foundCategoryIds = categoryRepository.findByIdIn(categoryIds);

        Assertions.assertEquals(expectedSize, foundCategoryIds.size());
    }
}
