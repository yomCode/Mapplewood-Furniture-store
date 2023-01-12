package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.ModeOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeOfPaymentRepository extends JpaRepository<ModeOfPayment, Long> {

    Optional<ModeOfPayment> findByName(String name);
}