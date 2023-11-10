package com.store.book.service;

import static org.mockito.ArgumentMatchers.any;

import com.store.book.dto.book.BookDto;
import com.store.book.dto.book.BookDtoWithoutCategoryIds;
import com.store.book.dto.book.CreateBookRequestDto;
import com.store.book.exception.EntityNotFoundException;
import com.store.book.mapper.BookMapper;
import com.store.book.model.Book;
import com.store.book.model.Category;
import com.store.book.repository.book.BookRepository;
import com.store.book.repository.category.CategoryRepository;
import com.store.book.service.book.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Getting book by id when book id is 1")
    void findByBookId_ValidBookId_ReturnsBookDto() {
        Long bookId = 1L;
        Book mockedBook = new Book();
        mockedBook.setId(bookId);
        mockedBook.setTitle("Kobzar");
        mockedBook.setAuthor("Shevchenko");
        mockedBook.setIsbn("767698734gygsfg897");
        mockedBook.setPrice(BigDecimal.valueOf(100));

        BookDto expectedBook = new BookDto();
        expectedBook.setId(mockedBook.getId());
        expectedBook.setTitle(mockedBook.getTitle());
        expectedBook.setAuthor(mockedBook.getAuthor());
        expectedBook.setIsbn(mockedBook.getIsbn());
        expectedBook.setPrice(mockedBook.getPrice());

        Mockito.when(bookRepository.findBookById(bookId)).thenReturn(Optional.of(mockedBook));
        Mockito.when(bookMapper.toDto(mockedBook)).thenReturn(expectedBook);

        BookDto actualBook = bookService.findByBookId(bookId);

        Assertions.assertEquals(expectedBook, actualBook);
    }

    @Test
    @DisplayName("Getting error message when book id is not exist")
    void findByBookId_InvalidBookId_ReturnsErrorMessage() {
        Long bookId = 100L;

        Mockito.when(bookRepository.findBookById(bookId)).thenReturn(Optional.empty());

        EntityNotFoundException actualError = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> bookService.findByBookId(bookId)
        );

        String expectedErrorMessage = "Can't find book by id: " + bookId;

        Assertions.assertEquals(expectedErrorMessage, actualError.getMessage());
    }

    @Test
    @DisplayName("Getting all books when data exists")
    void findAll_CheckData_ReturnsRecordsSizeTwo() {
        Book mockedFirstBook = new Book();
        mockedFirstBook.setId(1L);
        mockedFirstBook.setTitle("Kobzar");
        mockedFirstBook.setAuthor("Shevchenko");
        mockedFirstBook.setIsbn("767698734gygsfg897");
        mockedFirstBook.setPrice(BigDecimal.valueOf(100));

        Book mockedSecondBook = new Book();
        mockedSecondBook.setId(2L);
        mockedSecondBook.setTitle("One Way");
        mockedSecondBook.setAuthor("Smith");
        mockedSecondBook.setIsbn("4564l5nojh0thj0rt");
        mockedSecondBook.setPrice(BigDecimal.valueOf(100));

        List<Book> mockedBookList = new ArrayList<>();
        mockedBookList.addAll(List.of(mockedFirstBook, mockedSecondBook));

        Pageable pageable = PageRequest.of(0, 10);

        Mockito.when(bookRepository.findAllBooks(pageable)).thenReturn(mockedBookList);

        List<BookDto> expectedBookDtos = mockedBookList.stream()
                .map(bookMapper::toDto)
                .toList();

        List<BookDto> actualBookList = bookService.findAll(pageable);

        Assertions.assertEquals(expectedBookDtos.size(), actualBookList.size());
        Assertions.assertEquals(expectedBookDtos, actualBookList);
    }

    @Test
    @DisplayName("Saving book when data is correct")
    void save_CheckSaving_ReturnBookDto() {
        Category mockedCategory = new Category();
        mockedCategory.setId(1L);
        mockedCategory.setName("History");

        Set<Long> categoryIds = Set.of(1L);

        CreateBookRequestDto createNewBook = new CreateBookRequestDto();
        createNewBook.setTitle("One Way");
        createNewBook.setAuthor("Smith");
        createNewBook.setIsbn("4564l5nojh0thj0rt");
        createNewBook.setCategoryIds(categoryIds);
        createNewBook.setPrice(BigDecimal.valueOf(100));
        createNewBook.setCoverImage("test.png");
        createNewBook.setDescription("Test");

        Book mockedBook = new Book();
        mockedBook.setTitle(createNewBook.getTitle());
        mockedBook.setAuthor(createNewBook.getAuthor());
        mockedBook.setIsbn(createNewBook.getIsbn());
        mockedBook.setPrice(createNewBook.getPrice());
        mockedBook.setCategories(Set.of(mockedCategory));
        createNewBook.setCoverImage("test.png");
        createNewBook.setDescription("Test");

        BookDto expectedBookDto = new BookDto();
        expectedBookDto.setTitle(mockedBook.getTitle());
        expectedBookDto.setPrice(mockedBook.getPrice());
        expectedBookDto.setIsbn(mockedBook.getIsbn());
        expectedBookDto.setCoverImage(mockedBook.getCoverImage());
        expectedBookDto.setDescription(mockedBook.getDescription());
        expectedBookDto.setAuthor(mockedBook.getAuthor());
        expectedBookDto.setCategoryIds(createNewBook.getCategoryIds());

        Mockito.when(categoryRepository.findByIdIn(categoryIds)).thenReturn(Set.of(mockedCategory));
        Mockito.when(bookMapper.toEntity(createNewBook)).thenReturn(mockedBook);
        Mockito.when(bookRepository.save(mockedBook)).thenReturn(mockedBook);
        Mockito.when(bookMapper.toDto(mockedBook)).thenReturn(expectedBookDto);
        Mockito.verify(categoryRepository).findByIdIn(categoryIds);
        Mockito.verify(bookMapper).toEntity(createNewBook);
        Mockito.verify(bookRepository).save(mockedBook);
        Mockito.verify(bookMapper).toDto(mockedBook);
        BookDto actualBookDto = bookService.save(createNewBook);
        Assertions.assertEquals(expectedBookDto, actualBookDto);
    }

    @Test
    @DisplayName("Get books without category by category is when category id exist")
    void findAllByCategoryId_ValidCategoryId_ReturnsListOfBookDtoWithoutCategories() {
        Long categoryId = 1L;

        BookDtoWithoutCategoryIds mockedFirstBook = new BookDtoWithoutCategoryIds();
        mockedFirstBook.setId(1L);
        BookDtoWithoutCategoryIds mockedSecondBook = new BookDtoWithoutCategoryIds();
        mockedSecondBook.setId(2L);

        Mockito.when(bookRepository.findAllByCategoryId(categoryId))
                .thenReturn(List.of(new Book(), new Book()));
        Mockito.when(bookMapper.toDtoWithoutCategories(any(Book.class)))
                .thenReturn(mockedFirstBook, mockedSecondBook);

        List<BookDtoWithoutCategoryIds> expected = List.of(mockedFirstBook, mockedSecondBook);
        List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoryId(categoryId);

        Assertions.assertEquals(expected, actual);
    }
}
