package com.decagon.OakLandv1be.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponseDto {

    private String fullName;
    private String address;
    private String phone;
    private Boolean isDefault;
}
