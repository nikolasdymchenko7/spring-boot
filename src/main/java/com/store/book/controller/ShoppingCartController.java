package com.store.book.controller;

import com.store.book.dto.cartitem.CartItemDto;
import com.store.book.dto.cartitem.CreateCartItemRequestDto;
import com.store.book.dto.cartitem.UpdateCartItemRequestDto;
import com.store.book.dto.shoppingcart.ShoppingCartDto;
import com.store.book.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/carts")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "Add new product",
            description = "Adding new product to shopping cart")
    public CartItemDto addCartItem(@RequestBody @Valid CreateCartItemRequestDto requestDto) {

        return shoppingCartService.addCartItem(requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "View shopping cart",
            description = "View cart items from the shopping cart")
    public ShoppingCartDto findAllUserItemsFromShoppingCart() {
        return shoppingCartService.findShoppingCartByUser();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update cart items", description = "Update existing cart item")
    public CartItemDto updateCartItem(@PathVariable Long cartItemId,
                                      @RequestBody UpdateCartItemRequestDto requestDto) {

        return shoppingCartService.update(cartItemId, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete book", description = "Delete book by id")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.deleteCartItem(cartItemId);
    }
}
