package com.store.book.mapper;

import com.store.book.config.MapperConfig;
import com.store.book.dto.cartitem.CartItemDto;
import com.store.book.dto.cartitem.CreateCartItemRequestDto;
import com.store.book.dto.cartitem.UpdateCartItemRequestDto;
import com.store.book.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", ignore = true)
    CartItem toEntity(UpdateCartItemRequestDto cartItem);

    @Mapping(target = "book.id", source = "bookId")
    CartItem toEntity(CreateCartItemRequestDto requestDto);
}
