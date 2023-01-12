package com.decagon.OakLandv1be.dto;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FundWalletResponseDto {

    private String fullName;
    private Double depositAmount;
    private Double newBalance;

}
