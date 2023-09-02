package com.store.book.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateBookRequestDto {
    @NonNull
    @Size(min = 3)
    private String title;
    @NonNull
    @Size(min = 2)
    private String author;
    @NonNull
    @Size(min = 10)
    private String isbn;
    @NonNull
    @Min(value = 0)
    private BigDecimal price;
    private String description;
    private String coverImage;
}
