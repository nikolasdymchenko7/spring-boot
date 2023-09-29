package com.store.book.service.shoppingcart.impl;

import com.store.book.dto.cartitem.CartItemDto;
import com.store.book.dto.cartitem.CreateCartItemRequestDto;
import com.store.book.dto.cartitem.UpdateCartItemRequestDto;
import com.store.book.dto.shoppingcart.ShoppingCartDto;
import com.store.book.exception.EntityNotFoundException;
import com.store.book.mapper.CartItemMapper;
import com.store.book.mapper.ShoppingCartMapper;
import com.store.book.model.Book;
import com.store.book.model.CartItem;
import com.store.book.model.ShoppingCart;
import com.store.book.model.User;
import com.store.book.repository.book.BookRepository;
import com.store.book.repository.cartitem.CartItemRepository;
import com.store.book.repository.shoppingcart.ShoppingCartRepository;
import com.store.book.repository.user.UserRepository;
import com.store.book.service.shoppingcart.ShoppingCartService;
import com.store.book.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;

    @Transactional
    @Override
    public ShoppingCartDto findShoppingCartByUser() {
        Long userId = userService.getCurrentUser().get().getId();
        ShoppingCart shoppingCart = getShoppingCartByUserId(userId);
        ShoppingCartDto dto = shoppingCartMapper.toDto(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Transactional
    @Override
    public CartItemDto addCartItem(CreateCartItemRequestDto requestDto) {
        User user = userService.getCurrentUser().get();
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(user.getId())
                .orElseGet(() -> registerNewShoppingCart(user));

        Book book = bookRepository.findBookById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find book by id: " + requestDto.getBookId()));

        CartItem entity = cartItemMapper.toEntity(requestDto);
        entity.setBook(book);
        entity.setShoppingCart(shoppingCart);
        return cartItemMapper.toDto(cartItemRepository.save(entity));
    }

    @Override
    public CartItemDto update(Long cartItemId,
                              UpdateCartItemRequestDto requestDto) {
        CartItem cartItemFromDb = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find entity by id: "
                        + cartItemId));
        CartItem cartItem = cartItemMapper.toEntity(requestDto);
        cartItemFromDb.setQuantity(cartItem.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItemFromDb));
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        User user = userService.getCurrentUser().get();
        ShoppingCart shoppingCart = shoppingCartRepository
                .findShoppingCartById(user.getId()).get();
        CartItem cartItem = shoppingCart.getCartItems()
                .stream().filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item by id "
                        + cartItemId + " in shopping cart"));
        cartItemRepository.delete(cartItem);
    }

    private ShoppingCart getShoppingCartByUserId(Long userId) {
        return shoppingCartRepository
                .findShoppingCartById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't "
                        + "get shopping cart with id " + userId));
    }

    private ShoppingCart registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return shoppingCartRepository.save(shoppingCart);
    }
}
