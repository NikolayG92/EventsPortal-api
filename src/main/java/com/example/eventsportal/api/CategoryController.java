package com.example.eventsportal.api;

import com.example.eventsportal.models.bindingModels.CategoryBindingModel;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.models.views.CategoryViewModel;
import com.example.eventsportal.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok()
                .body(this.categoryService
                        .getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryViewModel> findCategoryById(@PathVariable("id") String id) {

        return ResponseEntity
                .ok()
                .body(this.categoryService.findCategoryById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody
                                                               CategoryBindingModel categoryBindingModel){

        return ResponseEntity
                .ok()
                .body(this.categoryService.createCategory(categoryBindingModel));

    }
}
