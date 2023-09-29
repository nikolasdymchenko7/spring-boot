package com.store.book.service.shoppingcart;

import com.store.book.dto.cartitem.CartItemDto;
import com.store.book.dto.cartitem.CreateCartItemRequestDto;
import com.store.book.dto.cartitem.UpdateCartItemRequestDto;
import com.store.book.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto findShoppingCartByUser();

    CartItemDto addCartItem(CreateCartItemRequestDto requestDto);

    CartItemDto update(Long cartItemId,
                           UpdateCartItemRequestDto requestDto);

    void deleteCartItem(Long cartItemId);
}
