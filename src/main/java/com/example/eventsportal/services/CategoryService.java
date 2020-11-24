package com.example.eventsportal.services;

import com.example.eventsportal.models.dtos.CategoryDto;
import com.example.eventsportal.models.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getAllCategories();

    CategoryDto findCategoryById(String id);
}
