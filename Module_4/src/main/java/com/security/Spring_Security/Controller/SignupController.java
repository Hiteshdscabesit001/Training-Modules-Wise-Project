package com.security.Spring_Security.Controller;

import com.security.Spring_Security.Dto.SignupRequest;
import com.security.Spring_Security.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final AuthService authService;

    public SignupController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping
    public ResponseEntity<String> signupCustomer(@RequestBody SignupRequest signupRequest)
    {
        boolean isUserCreated = authService.createCustomer(signupRequest);
        if (isUserCreated)
        {
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer Created Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer is not Created!!");
        }
    }

}
