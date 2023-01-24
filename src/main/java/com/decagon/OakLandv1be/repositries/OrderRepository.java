package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}