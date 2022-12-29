package com.example.demotest.service;

import com.example.demotest.models.Product;
import com.example.demotest.repos.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    Optional<Product> getProductById(UUID id);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product updateProduct(UUID id, Product product);

    void deleteProduct(UUID id);

}

