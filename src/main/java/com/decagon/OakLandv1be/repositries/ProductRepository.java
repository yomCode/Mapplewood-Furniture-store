package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
