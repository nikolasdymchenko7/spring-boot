package com.store.book.repository.book;

import com.store.book.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories")
    List<Book> findAllBooks(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Specification<Book> spec, Pageable pageable);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories WHERE b.id = :bookId")
    Optional<Book> findBookById(Long bookId);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(Long categoryId);
}
