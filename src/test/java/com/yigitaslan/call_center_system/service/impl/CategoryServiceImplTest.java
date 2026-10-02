package com.yigitaslan.call_center_system.service.impl;

import com.yigitaslan.call_center_system.model.Category;
import com.yigitaslan.call_center_system.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        List<Category> categories = List.of(
                new Category(1, "Billing"),
                new Category(2, "Technical Support"));
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(categories, result);
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryById_WhenCategoryExists_ShouldReturnCategory() {
        Category category = new Category(1, "Billing");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Optional<Category> result = categoryService.getCategoryById(1);

        assertTrue(result.isPresent());
        assertEquals(category, result.get());
        verify(categoryRepository).findById(1);
    }

    @Test
    void getCategoryById_WhenCategoryDoesNotExist_ShouldReturnEmpty() {
        when(categoryRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Category> result = categoryService.getCategoryById(99);

        assertTrue(result.isEmpty());
        verify(categoryRepository).findById(99);
    }
}
