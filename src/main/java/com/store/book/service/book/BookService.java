package com.store.book.service.book;

import com.store.book.dto.book.BookDto;
import com.store.book.dto.book.BookSearchParameters;
import com.store.book.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findByBookId(Long id);

    List<BookDto> search(BookSearchParameters params);

    void deleteById(Long id);
}
