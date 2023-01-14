package com.decagon.OakLandv1be.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResponseManager {

    public ApiResponse<Object> success(Object data){
        return new ApiResponse<>("Request Successful", true, data);
    }

    public ApiResponse<Object> error(String errorMessage){

        return new ApiResponse<>(errorMessage,false,"null");
    }
}