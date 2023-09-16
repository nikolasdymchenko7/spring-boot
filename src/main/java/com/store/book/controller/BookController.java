package com.store.book.controller;

import com.store.book.dto.book.BookDto;
import com.store.book.dto.book.BookSearchParameters;
import com.store.book.dto.book.CreateBookRequestDto;
import com.store.book.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "Get all products", description = "Get a list of available products")
    @ApiResponse(responseCode = "200", description = "All books",
                    content = {@Content(mediaType = "application/json")})
    public List<BookDto> getAll(Authentication authentication, Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Get book by id")
    @ApiResponse(responseCode = "200", description = "Get book by id",
            content = {@Content(mediaType = "application/json")})
    public BookDto getBookById(Authentication authentication, @PathVariable Long id) {
        return bookService.findByBookId(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book")
    @ApiResponse(responseCode = "200", description = "Create new book",
            content = {@Content(mediaType = "application/json")})
    public BookDto createBook(Authentication authentication,
                              @RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update existing book")
    public BookDto updateBook(Authentication authentication, @PathVariable Long id,
                              @RequestBody CreateBookRequestDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/search")
    @Operation(summary = "Books search",
            description = "Book search by: title, author, isbn, price")
    public List<BookDto> search(BookSearchParameters searchParameters,
                                Pageable pageable) {
        return bookService.search(searchParameters, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete book by id")
    public void delete(Authentication authentication,
                       @PathVariable Long id) {
        bookService.deleteById(id);
    }
}
