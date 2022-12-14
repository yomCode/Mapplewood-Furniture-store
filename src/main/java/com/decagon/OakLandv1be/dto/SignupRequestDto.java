package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Email address is mandatory")
    @Email
    private String email;

    private String gender;

    @NotBlank(message = "Date of birth is mandatory")
    private String date_of_birth;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 11, max = 14)
    private String phoneNumber;

    @NotBlank(message = "Street number is mandatory")
    private String street;

    @NotBlank(message = "State is mandatory")
    private String state;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
