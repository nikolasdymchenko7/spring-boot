package com.store.book.dto.orders;

import com.store.book.model.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderDto {
    @NotNull
    private Order.Status status;
}
