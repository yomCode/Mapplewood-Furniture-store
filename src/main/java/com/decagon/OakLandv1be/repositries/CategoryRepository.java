package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}