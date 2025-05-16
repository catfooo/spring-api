package com.catfood.store.controllers;

import com.catfood.store.dtos.ProductDto;
import com.catfood.store.entities.Product;
import com.catfood.store.mappers.ProductMapper;
import com.catfood.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
//@RestController
@RequestMapping("/products")
public class ProductController2 {
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
            products = productRepository.findAllWithCategory();
        }
        return products.stream().map(productMapper::toDto).toList();
    }

    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

    @PostMapping
    ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        var product = productMapper.toEntity(productDto);
        productRepository.save(product);
        productDto.setId(product.getId());

//        return ResponseEntity.ok(productMapper.toDto(product));
        return ResponseEntity.ok(productDto);
    }
}
