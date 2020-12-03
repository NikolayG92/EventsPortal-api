package com.example.eventsportal.services.impl;

import com.example.eventsportal.models.bindingModels.CategoryBindingModel;
import com.example.eventsportal.models.dtos.CategoryDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.views.CategoryViewModel;
import com.example.eventsportal.models.views.EventViewModel;
import com.example.eventsportal.repositories.CategoryRepository;
import com.example.eventsportal.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Category> getAllCategories() {

        return new ArrayList<>(this.categoryRepository.findAll());

    }

    @Override
    public CategoryViewModel findCategoryById(String id) {

        Category category = this.categoryRepository
                .findById(id).orElse(null);

        if(category != null){
            CategoryViewModel categoryViewModel = this.modelMapper
                    .map(category, CategoryViewModel.class);
            categoryViewModel.setEvents(new ArrayList<>());

            category.getEvents()
                    .forEach(event -> {
                        EventViewModel eventViewModel = this.modelMapper
                                .map(event, EventViewModel.class);
                        categoryViewModel.getEvents().add(eventViewModel);
                    });

        return categoryViewModel;
    } else {
        throw new EntityNotFoundException("Category doesnt exist!");
        }
    }

    @Override
    public Category createCategory(CategoryBindingModel categoryBindingModel) {

        Category category = this.modelMapper
                .map(categoryBindingModel, Category.class);

        category.setEvents(new ArrayList<>());

        this.categoryRepository.saveAndFlush(category);

        return category;
    }

    @Override
    public Category findCategoryByName(String category) {

        return this.categoryRepository.findByName(category);
    }
}
