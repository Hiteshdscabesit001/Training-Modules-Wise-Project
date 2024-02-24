package com.hubspot.dto;

import com.hubspot.entity.Role;
import com.hubspot.enums.UserStatus;
import lombok.Data;

@Data
public class SignUpUserDto {

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
    private UserStatus status;

    private String otp;


}
