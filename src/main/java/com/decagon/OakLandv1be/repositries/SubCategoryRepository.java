package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findByName(String subCategory);
}
