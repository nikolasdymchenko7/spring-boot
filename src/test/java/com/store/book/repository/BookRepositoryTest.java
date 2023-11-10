package com.store.book.repository;

import com.store.book.model.Book;
import com.store.book.model.Category;
import com.store.book.repository.book.BookRepository;
import com.store.book.repository.category.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find book by title when title is Kobzar")
    void findBookById_WithValidBookById_ShouldReturnBookById() {
        Book expected = new Book();
        expected.setId(1L);
        expected.setTitle("Kobzar");
        expected.setAuthor("Shevchenko");
        expected.setIsbn("12334g3gkjnkjergu8");
        expected.setPrice(BigDecimal.valueOf(125));
        bookRepository.save(expected);

        Book actualBook = bookRepository.findBookById(expected.getId()).get();

        Assertions.assertEquals(expected, actualBook);
    }

    @Test
    @DisplayName("Find books by authors when author is Shevchenko")
    void findAllBooks_WithExistingBooks_ShouldReturnAllBooks() {

        Book kobzarBook = new Book();
        kobzarBook.setId(1L);
        kobzarBook.setTitle("Kobzar");
        kobzarBook.setAuthor("Shevchenko");
        kobzarBook.setIsbn("12334g3gkjnkjergu8");
        kobzarBook.setPrice(BigDecimal.valueOf(125));

        Book youAgainBook = new Book();
        youAgainBook.setId(1L);
        youAgainBook.setTitle("You, Again");
        youAgainBook.setAuthor("Kate Goldbeck");
        youAgainBook.setIsbn("1876987hbodf7v680s");
        youAgainBook.setPrice(BigDecimal.valueOf(100));
        bookRepository.saveAll(List.of(kobzarBook, youAgainBook));

        List<Book> actual = bookRepository.findAllBooks(Pageable.ofSize(10));

        Assertions.assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Find books by category id when category id is 1")
    void findAllByCategoryId_CheckBooks_ReturnBooksByCategoryId() {
        Category testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setName("Category1");
        categoryRepository.save(testCategory);

        Book testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test1");
        testBook.setAuthor("Author1");
        testBook.setIsbn("12334g3gkjnkjergu8");
        testBook.setPrice(BigDecimal.valueOf(125));
        testBook.setCategories(Set.of(testCategory));
        bookRepository.save(testBook);

        List<Book> actual = bookRepository.findAllByCategoryId(testCategory.getId());

        Assertions.assertEquals(1, actual.size());
    }
}
