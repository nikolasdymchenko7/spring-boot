package com.store.book.service;

import com.store.book.dto.BookDto;
import com.store.book.dto.BookSearchParameters;
import com.store.book.dto.CreateBookRequestDto;
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
