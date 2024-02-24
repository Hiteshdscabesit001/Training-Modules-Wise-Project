package com.micro.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "AccountType cannot be a null or empty")
    private String accountType;

    @NotEmpty(message = "BranchAddress should not be null or empty")
    private String branchAddress;
}
