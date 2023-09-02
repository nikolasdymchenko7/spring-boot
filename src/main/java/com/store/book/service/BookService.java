package com.store.book.service;

import com.store.book.dto.BookDto;
import com.store.book.dto.BookSearchParameters;
import com.store.book.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto findByBookId(Long id);

    List<BookDto> search(BookSearchParameters params);

    void deleteById(Long id);
}
