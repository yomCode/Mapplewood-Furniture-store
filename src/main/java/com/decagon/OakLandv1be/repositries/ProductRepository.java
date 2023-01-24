package com.decagon.OakLandv1be.repositries;
import com.decagon.OakLandv1be.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @Query(value="SELECT * FROM product_tbl WHERE sub_category_id=?", nativeQuery = true)
    Page<Product> findAllBySubCategoryId(Long subCategoryId, PageRequest of);
}
