package com.yigitaslan.call_center_system.service;

import com.yigitaslan.call_center_system.model.Category;
import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Integer id);
}
