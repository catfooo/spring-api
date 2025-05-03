package com.catfood.store.controllers;

import com.catfood.store.dtos.ProductDto;
import com.catfood.store.entities.Product;
import com.catfood.store.mappers.ProductMapper;
import com.catfood.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

//    public ProductController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId
    ) {
        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
//            products = productRepository.findAll();
            products = productRepository.findAllWithCategory();
        }
//        return productRepository.findAll().stream().map(productMapper::toDto).toList();
        return products.stream().map(productMapper::toDto).toList();
    }
}
