package com.hubspot.controller;

import com.hubspot.constants.UserConstant;
import com.hubspot.dto.*;
import com.hubspot.entity.SignUser;
import com.hubspot.repository.UserRepository;
import com.hubspot.service.IUserService;
import com.hubspot.utils.JwtUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUserService iUserService;

    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails;
        try {
            userDetails = UserRepository.loadUserByUsername(loginRequest.getEmail());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        assert userDetails != null;
        String jwt = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new LoginResponse(jwt));
    }


    @PostMapping("login")
    public String authenticateAndGetToken(@RequestBody LoginRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("getUser")
    @PreAuthorize("hasRole('USER')")
//    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Optional<SignUser>> getUser(@RequestParam
                                            @Pattern(regexp = "(^$|[0-9]{10})",
                                                    message = "Mobile Number must be 10 digits")

                                            String phoneNumber) {
        Optional<SignUser> user = iUserService.fetchUser(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @PutMapping("updateUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> updateUser(@Valid @RequestBody SignUpUserDto userDto) {
        boolean isUpdated = iUserService.updateUser(String.valueOf(userDto));
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200, UserConstant.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500, UserConstant.MESSAGE_500));
        }
    }


    @GetMapping("getAllUsers")
    //@PreAuthorize("hasAnyRole('ADMIN', 'ROLE_SUB_ADMIN')")
    public ResponseEntity<SignUser> fetchAllUsers() {

        try {

            List<SignUser> list = iUserService.fetchAllUsers();

            if(list.isEmpty())
            {
                return new ResponseEntity("User details are not present..",HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(list,HttpStatus.FOUND);
            }



        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity("This is Bad Request..",HttpStatus.BAD_REQUEST);

        }

    }

    @DeleteMapping("deleteUser")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto> deleteUserDetail(@RequestParam
                                                        @Pattern(regexp = "(^$|[0-9]{10})",
                                                                message = "Mobile Number must be 10 digits")
                                                        String phoneNumber)
    {
        boolean isDeleted = iUserService.deleteUser(phoneNumber);
        if (isDeleted)
        {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstant.STATUS_200,UserConstant.STATUS_200));
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(UserConstant.STATUS_500,UserConstant.MESSAGE_500));
        }
    }


    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam ForgetPasswordRequest request) {
        return iUserService.ForgetPassword(request.getEmail());
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidationRequest validationRequest) {
        return iUserService.validateOtp(validationRequest.getEmail(), validationRequest.getOtp());
    }

    @PostMapping("/reset-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return iUserService.resetPassword(resetPasswordRequest);
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return iUserService.changePassword(changePasswordRequest);
    }


}
