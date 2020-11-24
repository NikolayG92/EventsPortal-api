package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.dtos.CategoryDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<Category> getAllCategories() {

        return new HashSet<>(this.categoryRepository.findAll());

    }

    @Override
    public CategoryDto findCategoryById(String id) {

        Category category = this.categoryRepository
                .findById(id).orElse(null);
        CategoryDto categoryDto = this.modelMapper
                .map(category, CategoryDto.class);
        return categoryDto;
    }
}
