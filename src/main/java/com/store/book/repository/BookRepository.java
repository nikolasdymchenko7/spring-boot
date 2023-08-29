package com.store.book.repository;

import com.store.book.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findByBookId(Long id);

    List<Book> findAll();
}
