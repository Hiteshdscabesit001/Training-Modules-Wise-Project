package com.micro.accounts.controller;

import com.micro.accounts.constants.AccountsConstant;
import com.micro.accounts.dto.CustomerDto;
import com.micro.accounts.dto.ResponseDto;
import com.micro.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Validated
public class AccountController {


    private IAccountsService iAccountsService;


    @GetMapping("sayHello")
    public String sayHello()
    {
        return "Hello .....";
    }

    @PostMapping("create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto)
    {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstant.STATUS_201,AccountsConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})",
                                                                       message = "Mobile Number must be 10 digits")

                                                               String mobileNumber)
    {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetail(@Valid @RequestBody CustomerDto customerDto)
    {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated)
        {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }
        else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstant.STATUS_500,AccountsConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetail(@RequestParam
                                                               @Pattern(regexp = "(^$|[0-9]{10})",
                                                                       message = "Mobile Number must be 10 digits")
                                                               String mobileNumber)
    {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted)
        {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.STATUS_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstant.STATUS_500,AccountsConstant.MESSAGE_500));
        }
    }
}
