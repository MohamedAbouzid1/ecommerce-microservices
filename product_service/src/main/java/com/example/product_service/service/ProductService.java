package com.example.product_service.service;

import com.example.product_service.entity.Product;
import com.example.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }
    // create
    public Product create(Product product) {
        validateProduct(product);
        return repo.save(product);
    }
    // read all
    public List<Product> findAll() {
        return repo.findAll();
    }
    // read by id
    public Product findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }
    // update
    public Product update(Long id, Product updated) {
        Product existing = findById(id);
        validateProduct(updated);

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());

        return repo.save(existing);
    }
    //delete
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Product not found with id: " + id);
        }
        repo.deleteById(id);
    }
    // business validation
    private void validateProduct(Product product) {
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }
}

