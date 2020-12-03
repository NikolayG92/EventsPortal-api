package com.example.eventsportal.services;

import com.example.eventsportal.models.bindingModels.CategoryBindingModel;
import com.example.eventsportal.models.dtos.CategoryDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.views.CategoryViewModel;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<Category> getAllCategories();

    CategoryViewModel findCategoryById(String id);

    Category createCategory(CategoryBindingModel categoryBindingModel);

    Category findCategoryByName(String category);
}
