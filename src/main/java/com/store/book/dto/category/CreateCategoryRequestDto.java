package com.store.book.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCategoryRequestDto {
    @Size(min = 3)
    private String name;
    private String description;
}
