package com.security.Spring_Security.Controller;

import com.security.Spring_Security.Dto.LoginRequest;
import com.security.Spring_Security.Dto.LoginResponse;
import com.security.Spring_Security.Service.jwt.CustomerServiceImpl;
import com.security.Spring_Security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final CustomerServiceImpl customerService;
    private final JwtUtil jwtUtil;
    @Autowired
    public LoginController(AuthenticationManager authenticationManager, CustomerServiceImpl customerService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

   // @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword())
            );

        } catch (AuthenticationException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails;
        try {
            userDetails = customerService.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new LoginResponse(jwt));
    }


    @PostMapping
    public String authenticateAndGetToken(@RequestBody LoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }


}
