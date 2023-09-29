package com.store.book.dto.cartitem;

import lombok.Data;

@Data
public class CartItemResponseDto {
    private Long bookId;
    private int quantity;
}
