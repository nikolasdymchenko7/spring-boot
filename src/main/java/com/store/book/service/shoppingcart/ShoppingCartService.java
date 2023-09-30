package com.store.book.service.shoppingcart;

import com.store.book.dto.cartitem.CartItemDto;
import com.store.book.dto.cartitem.CreateCartItemRequestDto;
import com.store.book.dto.cartitem.UpdateCartItemRequestDto;
import com.store.book.dto.shoppingcart.ShoppingCartDto;
import com.store.book.model.User;

public interface ShoppingCartService {
    ShoppingCartDto findShoppingCartByUser(Long userId);

    CartItemDto addCartItem(User user,
                            CreateCartItemRequestDto requestDto);

    CartItemDto update(Long cartItemId,
                           UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long userId, Long cartItemId);
}
