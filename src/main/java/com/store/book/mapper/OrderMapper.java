package com.store.book.mapper;

import com.store.book.config.MapperConfig;
import com.store.book.dto.orders.OrderResponseDto;
import com.store.book.dto.orders.UpdateOrderDto;
import com.store.book.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    Order toModel(UpdateOrderDto orderDto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponseDto toDto(Order order);
}
