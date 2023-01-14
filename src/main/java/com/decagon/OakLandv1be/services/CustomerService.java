package com.decagon.OakLandv1be.services;

import com.decagon.OakLandv1be.dto.*;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.exceptions.AlreadyExistsException;
import com.decagon.OakLandv1be.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface CustomerService {
    SignupResponseDto saveCustomer(SignupRequestDto signupRequestDto) throws AlreadyExistsException, IOException;

    ResponseEntity<ApiResponse> verifyRegistration(String token);
    public void removeProductFromFavorites(Long pid);
    ResponseEntity<String> deleteProductFromFavourites(FavoritesDto deleteFavoriteDto, Long pid, Long cid);
    void editProfile(EditProfileRequestDto editProfileRequestDto);
    void addProductToFavorites(Long pid);
    Customer getCurrentlyLoggedInUser();
  
}
