package com.catfood.store.repositories;

import com.catfood.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Byte categoryId);

//    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p ")
    List<Product> findAllWithCategory();
}