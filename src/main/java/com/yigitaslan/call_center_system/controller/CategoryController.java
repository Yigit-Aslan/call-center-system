package com.yigitaslan.call_center_system.controller;


import com.yigitaslan.call_center_system.model.Category;
import com.yigitaslan.call_center_system.service.ICategoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final ICategoryService categoryService;
    private final Executor apiExecutor;

    public CategoryController(ICategoryService _categoryService, @Qualifier("apiExecutor") Executor apiExecutor){
        categoryService = _categoryService;
        this.apiExecutor = apiExecutor;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<List<Category>>> getAllCategories(){
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(categoryService.getAllCategories()), apiExecutor);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Category>> getCategoryById(@PathVariable Integer id) {
        return CompletableFuture.supplyAsync(() -> categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()), apiExecutor);
    }
}
