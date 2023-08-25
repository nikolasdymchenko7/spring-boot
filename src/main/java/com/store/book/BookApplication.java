package com.store.book;

import com.store.book.model.Book;
import com.store.book.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Kobzar");
            book.setAuthor("Shevchenko");
            book.setIsbn("978-0-13-898756-1");
            book.setPrice(BigDecimal.valueOf(200));
            book.setDescription("Ukrainian book");

            bookService.save(book);

            bookService.findAll().forEach(System.out::println);
        };
    }
}
