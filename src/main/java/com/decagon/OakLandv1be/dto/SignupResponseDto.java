package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.entities.Address;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponseDto {
    private String firstName;
    private String lastName;
    private String Email;
    private Gender gender;
    private String date_of_birth;
    private String phone;
    private Boolean verificationStatus;
    private Address address;
}
