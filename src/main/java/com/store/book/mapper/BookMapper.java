package com.store.book.mapper;

import com.store.book.config.MapperConfig;
import com.store.book.dto.book.BookDto;
import com.store.book.dto.book.BookDtoWithoutCategoryIds;
import com.store.book.dto.book.CreateBookRequestDto;
import com.store.book.model.Book;
import com.store.book.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toEntity(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoriesIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book
                .getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }
}
