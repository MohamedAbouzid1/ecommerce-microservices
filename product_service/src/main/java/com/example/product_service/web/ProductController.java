package com.example.product_service.web;

import com.example.product_service.entity.Product;
import com.example.product_service.service.ProductService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    @GetMapping
    public List<ProductResponse> getAll() {
        return service.findAll()
                .stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        Product product = service.findById(id);
        return toProductResponse(product);
    }
    @PostMapping
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        Product product = toProductEntity(request);
        Product created = service.create(product);
        return toProductResponse(created);
    }
    // POST update
    @PostMapping("/{id}")
    public ProductResponse update(@Valid @PathVariable Long id,@RequestBody UpdateProductRequest request) {
        Product product = toProductEntity(request);
        Product updated = service.update(id, product);
        return toProductResponse(updated);
    }
    @DeleteMapping("/{id}")
    public void  delete(@PathVariable Long id) {
        service.delete(id);
    }

    // mapping methods
    private ProductResponse toProductResponse(Product product) {
        ProductResponse dto = new ProductResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCreatedAt(product.getCreatedAt());
        return dto;
    }
    private Product toProductEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        return product;
    }

    private Product toProductEntity(UpdateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        return product;
    }
}
