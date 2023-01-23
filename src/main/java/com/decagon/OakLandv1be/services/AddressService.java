package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.dto.AddressResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressService {
    AddressResponseDto createAddress(AddressRequestDto request);

    String updateAddress(Long address_id, AddressRequestDto request);

    void setAsDefault(Long address_id);

    void DeleteAddress(Long addressId);

    List<AddressResponseDto> getAllAddress();
}
