package com.example.product_service.web;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import com.example.product_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return service.findById(id);
    }
    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }
    // POST update
    @PostMapping("/{id}")
    public Product update(@PathVariable Long id,@RequestBody Product product) {
        return service.update(id, product);
    }
    @DeleteMapping("/{id}")
    public void  delete(@PathVariable Long id) {
        service.delete(id);
    }
}
