package com.labellens.controller;

import com.labellens.model.Product;
import com.labellens.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProducts(@RequestParam(required = false) String search,
                                        @RequestParam(required = false) String category) {
        if (search != null && !search.trim().isEmpty()) {
            return productRepository.searchProducts(search.trim());
        }
        if (category != null && !category.trim().equalsIgnoreCase("ALL")) {
            return productRepository.findByCategoryIgnoreCase(category.trim());
        }
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<?> getProductByBarcode(@PathVariable String barcode) {
        return productRepository.findByBarcode(barcode)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
