package com.labellens.controller;

import com.labellens.model.Ingredient;
import com.labellens.repository.IngredientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping
    public List<Ingredient> getAllIngredients(@RequestParam(required = false) String category,
                                             @RequestParam(required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            return ingredientRepository.searchIngredients(search.trim());
        }
        if (category != null && !category.trim().equalsIgnoreCase("ALL")) {
            return ingredientRepository.findByCategoryIgnoreCase(category.trim());
        }
        return ingredientRepository.findAll();
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return ingredientRepository.findDistinctCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Long id) {
        return ingredientRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<?> getIngredientByCode(@PathVariable String code) {
        return ingredientRepository.findByAdditiveCodeIgnoreCase(code)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
