package com.example.eventsportal.api;

import com.example.eventsportal.models.dtos.CategoryDto;
import com.example.eventsportal.models.dtos.UserDto;
import com.example.eventsportal.models.entities.Category;
import com.example.eventsportal.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/all")
    public ResponseEntity<Set<Category>> getAllCategories() {

        return ResponseEntity.ok()
                .body(this.categoryService
                        .getAllCategories());
    }

    @GetMapping("/")
    public ResponseEntity<CategoryDto> findCategoryById(@RequestParam("id") String id) {

        CategoryDto category = this.categoryService.findCategoryById(id);

        if (category != null) {
            return ResponseEntity
                    .ok()
                    .body(category);
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
}
