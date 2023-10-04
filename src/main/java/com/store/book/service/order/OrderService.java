package com.store.book.service.order;

import com.store.book.dto.orderitem.OrderItemResponseDto;
import com.store.book.dto.orders.OrderResponseDto;
import com.store.book.dto.orders.UpdateOrderDto;
import com.store.book.model.User;
import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(User user);

    OrderResponseDto updateOrderStatus(Long orderId, UpdateOrderDto orderDto);

    OrderItemResponseDto findOrderItemByOrderIdAndItemId(Long userId, Long orderId, Long itemId);

    List<OrderResponseDto> getOrders(Long userId);

    List<OrderItemResponseDto> getOrderItemsByOrderId(Long userId, Long orderId);
}
