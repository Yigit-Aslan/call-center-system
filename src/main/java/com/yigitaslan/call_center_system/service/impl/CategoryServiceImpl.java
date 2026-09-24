package com.yigitaslan.call_center_system.service.impl;


import com.yigitaslan.call_center_system.model.Category;
import com.yigitaslan.call_center_system.model.SupportTicket;
import com.yigitaslan.call_center_system.repository.ICategoryRepository;
import com.yigitaslan.call_center_system.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;

    public CategoryServiceImpl(ICategoryRepository _categoryRepository)
    {
        categoryRepository = _categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }
}
