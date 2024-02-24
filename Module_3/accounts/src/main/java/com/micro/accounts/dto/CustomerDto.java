package com.micro.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name is required")
    @Size(min = 6,max = 15, message = "The length of customer name should between 6 and 15")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email address will be in valid format")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
