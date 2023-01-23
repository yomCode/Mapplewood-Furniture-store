package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
    @Query(value = "SELECT * FROM product_tbl ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
    List<Product> findProductByCreatedAtDesc();
    @Query(value = "SELECT * FROM product_tbl ORDER BY sales DESC LIMIT 3", nativeQuery = true)
           List <Product> findProductsBySalesDesc();
}
