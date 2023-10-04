package com.store.book.mapper;

import com.store.book.config.MapperConfig;
import com.store.book.dto.orderitem.OrderItemResponseDto;
import com.store.book.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
