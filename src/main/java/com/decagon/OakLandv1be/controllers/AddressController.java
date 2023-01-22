package com.decagon.OakLandv1be.controllers;

import com.decagon.OakLandv1be.dto.AddressRequestDto;
import com.decagon.OakLandv1be.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {

    private final AddressService addressService;


    @PostMapping("/new")
    public ResponseEntity<Object> createAddress(@RequestBody AddressRequestDto request){
        return ResponseEntity.ok(addressService.createAddress(request));
    }

    @PutMapping("/update/{addressId}")
    public ResponseEntity<String> updateAddress(@PathVariable Long addressId, @RequestBody AddressRequestDto request){
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }


    @GetMapping("/setDefault/{addressId}")
    public ResponseEntity<Object> setAsDefault(@PathVariable Long addressId){
        addressService.setAsDefault(addressId);
        return ResponseEntity.ok("Address set to default");
    }


    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<Object> deleteAddress(@PathVariable Long addressId){
        addressService.DeleteAddress(addressId);
        return ResponseEntity.ok("Address deleted");
    }


    @GetMapping("/all")
    public ResponseEntity<Object> getAllAddress(){
        return ResponseEntity.ok(addressService.getAllAddress());
    }

}
