package com.hubspot.controller;

import com.hubspot.dto.SignUpUserDto;
import com.hubspot.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SignUpController {

    private final IUserService iUserService;


    @PostMapping("signup")
    public ResponseEntity<String> signUpUser(@RequestBody SignUpUserDto userDto) {
        boolean isUserCreated = iUserService.signUpUser(userDto);
        if (isUserCreated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User SignUp Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is not SignUp!!");
        }
    }
}

