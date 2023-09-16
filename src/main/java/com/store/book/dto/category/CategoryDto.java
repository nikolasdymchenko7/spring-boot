package com.store.book.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
