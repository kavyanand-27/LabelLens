package com.labellens.controller;

import com.labellens.model.Product;
import com.labellens.repository.ProductRepository;
import com.labellens.service.IngredientMatchingEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/comparisons")
public class ComparisonController {

    private final ProductRepository productRepository;
    private final IngredientMatchingEngine matchingEngine;

    public ComparisonController(ProductRepository productRepository,
                                IngredientMatchingEngine matchingEngine) {
        this.productRepository = productRepository;
        this.matchingEngine = matchingEngine;
    }

    @PostMapping
    public ResponseEntity<?> compareProducts(@RequestBody Map<String, List<Long>> req) {
        List<Long> productIds = req.get("productIds");
        if (productIds == null || productIds.size() < 2) {
            return ResponseEntity.badRequest().body(Map.of("error", "At least two product IDs must be provided for comparison."));
        }

        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() < 2) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unable to locate the specified products."));
        }

        Product p1 = products.get(0);
        Product p2 = products.get(1);

        List<String> t1 = matchingEngine.tokenizeIngredients(p1.getRawIngredients());
        List<String> t2 = matchingEngine.tokenizeIngredients(p2.getRawIngredients());

        Set<String> set1 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        set1.addAll(t1);

        Set<String> set2 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        set2.addAll(t2);

        Set<String> shared = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String s : set1) {
            if (set2.contains(s)) shared.add(s);
        }

        Set<String> onlyP1 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String s : set1) {
            if (!set2.contains(s)) onlyP1.add(s);
        }

        Set<String> onlyP2 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String s : set2) {
            if (!set1.contains(s)) onlyP2.add(s);
        }

        Map<String, Object> comparison = new LinkedHashMap<>();
        comparison.put("product1", p1);
        comparison.put("product2", p2);
        comparison.put("sharedIngredients", shared);
        comparison.put("product1UniqueIngredients", onlyP1);
        comparison.put("product2UniqueIngredients", onlyP2);
        comparison.put("ingredientCountDifference", set1.size() - set2.size());

        return ResponseEntity.ok(comparison);
    }
}
