package com.decagon.OakLandv1be.repositries;

import com.decagon.OakLandv1be.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}