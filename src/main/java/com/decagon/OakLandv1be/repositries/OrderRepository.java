package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Order;
import com.decagon.OakLandv1be.enums.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
   Page<Order> findByDeliveryStatus(DeliveryStatus status, Pageable pageable);
}