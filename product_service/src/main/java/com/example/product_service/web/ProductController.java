package com.example.product_service.web;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }
    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }
}
