package com.store.book.dto.cartitem;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {
    @NotEmpty
    private Integer quantity;
}
