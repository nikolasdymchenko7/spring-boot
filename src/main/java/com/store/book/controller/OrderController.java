package com.store.book.controller;

import com.store.book.dto.orderitem.OrderItemResponseDto;
import com.store.book.dto.orders.CreateOrderResponseDto;
import com.store.book.dto.orders.OrderResponseDto;
import com.store.book.dto.orders.UpdateOrderDto;
import com.store.book.model.User;
import com.store.book.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "New order", description = "Create a new order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    CreateOrderResponseDto createOrder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return orderService.createOrder(user);
    }

    @Operation(summary = "Update order", description = "Update existing order")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    UpdateOrderDto updateOrderStatus(@PathVariable Long id,
                                     @RequestBody @Valid UpdateOrderDto orderDto) {
        return orderService.updateOrderStatus(id, orderDto);
    }

    @Operation(summary = "View user's order history",
            description = "Retrieve user's order history")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    List<OrderResponseDto> getOrdersByUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return orderService.getOrders(user.getId());
    }

    @Operation(summary = "View items by order",
            description = "View items by a specific order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("{orderId}/items")
    List<OrderItemResponseDto> getOrderItemByOrderId(Authentication authentication,
                                                     @PathVariable Long orderId) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemsByOrderId(user.getId(), orderId);
    }

    @Operation(summary = "View item details",
            description = "View item details by specific order")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("{orderId}/items/{itemId}")
    OrderItemResponseDto getOrderItem(Authentication authentication,
                                      @PathVariable Long orderId,
                                      @PathVariable Long itemId) {
        User user = (User) authentication.getPrincipal();

        return orderService.findOrderItemByOrderIdAndItemId(user.getId(), orderId, itemId);
    }

}
