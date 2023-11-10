package com.store.book.service;

import com.store.book.dto.category.CategoryDto;
import com.store.book.dto.category.CreateCategoryRequestDto;
import com.store.book.exception.EntityNotFoundException;
import com.store.book.mapper.CategoryMapper;
import com.store.book.model.Category;
import com.store.book.repository.category.CategoryRepository;
import com.store.book.service.category.impl.CategoryServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Getting category by id when category id is 1")
    void findCategoryById_ValidCategoryId_ReturnsCategoryById() {
        Long categoryId = 1L;
        Category mockCategory = new Category();
        mockCategory.setId(categoryId);
        mockCategory.setName("history");
        mockCategory.setDescription("Historical books");

        CategoryDto expectedCategoryDto = new CategoryDto();
        expectedCategoryDto.setId(categoryId);
        expectedCategoryDto.setName("history");
        expectedCategoryDto.setDescription("Historical books");

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));
        Mockito.when(categoryMapper.toDto(mockCategory)).thenReturn(expectedCategoryDto);

        CategoryDto actualCategoryDto = categoryService.getById(categoryId);

        Assertions.assertEquals(expectedCategoryDto, actualCategoryDto);
    }

    @Test
    @DisplayName("Getting error message when category ID is not exist")
    void findCategoryById_InvalidCategoryId_ReturnsCategoryById() {
        Long categoryId = 10L;

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(categoryId)
        );

        String expectedErrorMessage = "Can't find category by id " + categoryId;

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Saving category when data is correct")
    void save_CheckSaving_ReturnCategoryDto() {
        CreateCategoryRequestDto createNewCategory = new CreateCategoryRequestDto();
        createNewCategory.setName("history");
        createNewCategory.setDescription("Historical books");

        Category category = new Category();
        category.setName(createNewCategory.getName());
        category.setDescription(createNewCategory.getDescription());

        Mockito.when(categoryMapper.toEntity(createNewCategory)).thenReturn(category);
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto expectedCategory = categoryMapper.toDto(category);
        CategoryDto actualCategory = categoryService.save(createNewCategory);

        Assertions.assertEquals(expectedCategory, actualCategory);
    }

    @Test
    @DisplayName("Check update category when category id exist")
    void update_CheckUpdate_ReturnCategoryDto() {
        Long categoryId = 1L;
        Category mockedCategory = new Category();
        mockedCategory.setId(categoryId);
        mockedCategory.setName("Old category");
        mockedCategory.setDescription("Old description");

        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Updated category");
        requestDto.setDescription("Updated description");

        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setName(requestDto.getName());
        updatedCategory.setDescription(requestDto.getDescription());

        CategoryDto expectedCategoryDto = new CategoryDto();
        expectedCategoryDto.setId(updatedCategory.getId());
        expectedCategoryDto.setName(updatedCategory.getName());
        expectedCategoryDto.setDescription(updatedCategory.getDescription());

        Mockito.when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(mockedCategory));
        Mockito.when(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory);
        Mockito.when(categoryMapper.toDto(updatedCategory)).thenReturn(expectedCategoryDto);

        CategoryDto actualCategoryDto = categoryService.update(categoryId, requestDto);

        Assertions.assertEquals(expectedCategoryDto, actualCategoryDto);
    }
}
