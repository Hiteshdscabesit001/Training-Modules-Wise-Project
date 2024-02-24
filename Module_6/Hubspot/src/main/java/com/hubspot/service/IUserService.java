package com.hubspot.service;

import com.hubspot.dto.ChangePasswordRequest;
import com.hubspot.dto.ResetPasswordRequest;
import com.hubspot.dto.SignUpUserDto;
import com.hubspot.entity.SignUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    SignUser createUser(SignUpUserDto userDto);

    Optional<SignUser> fetchUser(String phoneNumber);

    boolean updateUser(String phoneNumber);

    boolean deleteUser(String phoneNumber);


    List<SignUser> fetchAllUsers();

    boolean signUpUser(SignUpUserDto userDto);

    ResponseEntity<String> ForgetPassword(String email);

    ResponseEntity<String> validateOtp(String email, String otp);

    ResponseEntity<String> resetPassword(ResetPasswordRequest resetPasswordRequest);

    ResponseEntity<String> changePassword(ChangePasswordRequest changePasswordRequest);
}
