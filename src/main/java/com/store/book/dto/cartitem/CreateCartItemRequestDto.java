package com.store.book.dto.cartitem;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CreateCartItemRequestDto {
    @Min(value = 1)
    private Long bookId;
    @Min(value = 1)
    private int quantity;
}
