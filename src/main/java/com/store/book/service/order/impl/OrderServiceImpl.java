package com.store.book.service.order.impl;

import com.store.book.dto.orderitem.OrderItemResponseDto;
import com.store.book.dto.orders.CreateOrderResponseDto;
import com.store.book.dto.orders.OrderResponseDto;
import com.store.book.dto.orders.UpdateOrderDto;
import com.store.book.exception.EntityNotFoundException;
import com.store.book.mapper.OrderItemMapper;
import com.store.book.mapper.OrderMapper;
import com.store.book.model.CartItem;
import com.store.book.model.Order;
import com.store.book.model.OrderItem;
import com.store.book.model.ShoppingCart;
import com.store.book.model.User;
import com.store.book.repository.cartitem.CartItemRepository;
import com.store.book.repository.order.OrderRepository;
import com.store.book.repository.orderitem.OrderItemRepository;
import com.store.book.repository.shoppingcart.ShoppingCartRepository;
import com.store.book.service.order.OrderService;
import com.store.book.service.shoppingcart.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public CreateOrderResponseDto createOrder(User user) {
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(Order.Status.CREATED);
        newOrder.setShippingAddress(user.getShippingAddress());

        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUserId(user.getId());
        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(c -> convertToOrderItem(c, newOrder))
                .collect(Collectors.toSet());
        newOrder.setOrderItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(o -> o.getPrice().multiply(BigDecimal.valueOf(o.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newOrder.setTotal(total);

        orderRepository.save(newOrder);
        shoppingCartService.clearShoppingCart(shoppingCart);

        return orderMapper.toCreateDto(newOrder);
    }

    @Override
    public UpdateOrderDto updateOrderStatus(Long orderId,
                                            UpdateOrderDto orderDto) {
        Order orderFromDb = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id " + orderId));
        Order model = orderMapper.toModel(orderDto);
        orderFromDb.setStatus(model.getStatus());

        return orderMapper.toUpdateDto(orderRepository.save(orderFromDb));
    }

    @Override
    public OrderItemResponseDto findOrderItemByOrderIdAndItemId(Long userId,
                                                                Long orderId,
                                                                Long itemId) {
        OrderItem orderItem = orderItemRepository
                .findByIdAndUserIdAndOrderId(orderId, userId, itemId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id "
                        + orderId + " and itemId " + itemId));
        return orderItemMapper.toDto(orderItem);
    }

    @Transactional
    @Override
    public List<OrderResponseDto> getOrders(Long userId) {
        List<Order> ordersByUserId = orderRepository.findAllByUserId(userId);
        return ordersByUserId.stream()
                .map(o -> orderMapper.toDto(o))
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long userId, Long orderId) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find order items by order id " + orderId));
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    private OrderItem convertToOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }
}
