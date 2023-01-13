package com.decagon.OakLandv1be.dto;

import com.decagon.OakLandv1be.enums.Gender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EditProfileRequestDto {
    @NotNull(message = "Please Provide firstname")
    private String firstName;

    @NotNull(message = "Please Provide lastname")
    private String lastName;

    @NotNull(message = "Please select your gender")
    private String gender;

    @Email
    @NotNull(message = "Please select your gender")
    private String email;

    @NotNull(message = "Please enter your date of birth")
    private String date_of_birth;

    @NotNull(message = "Please enter your phone number")
    private String phone;
}
