package com.yigitaslan.call_center_system.controller;

import com.yigitaslan.call_center_system.model.Category;
import com.yigitaslan.call_center_system.service.ICategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Test
    void getAllCategories_ShouldReturnOkWithCategories() {
        List<Category> categories = List.of(new Category(1, "Billing"));
        when(categoryService.getAllCategories()).thenReturn(categories);

        var response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryService).getAllCategories();
    }

    @Test
    void getCategoryById_WhenCategoryExists_ShouldReturnOkWithCategory() {
        Category category = new Category(1, "Billing");
        when(categoryService.getCategoryById(1)).thenReturn(Optional.of(category));

        var response = categoryController.getCategoryById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService).getCategoryById(1);
    }

    @Test
    void getCategoryById_WhenCategoryDoesNotExist_ShouldReturnNotFound() {
        when(categoryService.getCategoryById(99)).thenReturn(Optional.empty());

        var response = categoryController.getCategoryById(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoryService).getCategoryById(99);
    }
}
