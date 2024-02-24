package com.hubspot.service.impl;

import com.hubspot.dto.ChangePasswordRequest;
import com.hubspot.dto.ResetPasswordRequest;
import com.hubspot.dto.SignUpUserDto;
import com.hubspot.entity.SignUser;
import com.hubspot.exception.UserAlreadyExistsException;
import com.hubspot.mapper.UserMapper;
import com.hubspot.repository.RoleRepository;
import com.hubspot.repository.UserRepository;
import com.hubspot.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Autowired
    private OtpServiceImpl otpService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public SignUser createUser(SignUpUserDto userDto) {
        SignUser user = UserMapper.mapToUser(userDto,new SignUser());
        Optional<SignUser> optionalUser =userRepository.findByPhoneNumber(userDto.getPhoneNumber());

        if (optionalUser.isEmpty()){
            throw new UserAlreadyExistsException("Customer already register with given mobileNumber");
        }

        SignUser savedUser = userRepository.save(user);
        userRepository.save(createNewUser(savedUser));

        return user;
    }

    // User created
    private SignUser createNewUser(SignUser user){
        SignUser newUser = new SignUser();

        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setGender(user.getGender());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        newUser.setStatus(user.getStatus());
        newUser.setPhoneNumber(user.getPhoneNumber());
        return newUser;
    }


    // User created
//    @Override
//    public SignUser createUser(SignUpUserDto userDto) {
//        Role role = new Role();
//        if (userDto.getRole().equals("USER"))
//            role = roleRepository.findByRole("USER");
//        else if (userDto.getRole().equals("ADMIN"))
//            role = roleRepository.findByRole("ADMIN");
//
//        SignUser user = new SignUser();
//        user.setFirstName(userDto.getFirstName());
//        user.setLastName(userDto.getLastName());
//        user.setEmail(userDto.getEmail());
//        user.setGender(userDto.getGender());
//        user.setPassword(encoder.encode(userDto.getPassword()));
//        user.setRole(userDto.getRole());
//        user.setStatus(userDto.getStatus());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        return userRepository.save(user);
//
//    }

    //    fetch user details by mobile number
    @Override
    public Optional<SignUser> fetchUser(@RequestParam String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }


    // fetch all users
    public List<SignUser> fetchAllUsers() {
        return userRepository.findAll();
    }

    // update user
    public boolean updateUser(@PathVariable String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber);
        return true;
    }

    //    delete user
    @Override
    public boolean deleteUser(String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber);
        return true;
    }

    // for Signup user, any user can sign without any authentication

    @Override
    public boolean signUpUser(SignUpUserDto userDto) {

        // check if customer already exist
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            return false;
        }

        SignUser user = new SignUser();
        BeanUtils.copyProperties(userDto, user);
        // for password encoder
        String hashPassword = encoder.encode(userDto.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);
        return true;
    }


    // forget password
    @Override
    public ResponseEntity<String> ForgetPassword(String email) {
        // Retrieve user by email
        SignUser user = userRepository.findByEmail(email);

        // Validate user existence
        if (user == null) {
            return new ResponseEntity<>("User with the provided email does not exist.", HttpStatus.NOT_FOUND);
        }

        // Generate OTP
        String otp = otpService.generateOtp();

        // Store the securely hashed OTP in the database
        user.setOtp(bCryptPasswordEncoder.encode(otp));

        userRepository.save(user); // Save the updated user entity

        // Send OTP via email
        otpService.sendOtpByEmail(email, otp);

        return new ResponseEntity<>("OTP sent successfully. Check your email.", HttpStatus.OK);
    }

    // validate otp
    @Override
    public ResponseEntity<String> validateOtp(String email, String otp) {
        // Retrieve user by email
        SignUser user = userRepository.findByEmail(email);

        // Validate user existence
        if (user == null) {
            return new ResponseEntity<>("User with the provided email does not exist.", HttpStatus.NOT_FOUND);
        }

        // Validate OTP
        if (bCryptPasswordEncoder.matches(otp, user.getOtp())) {
            // The OTP is valid, you can proceed with password reset or other actions
            return new ResponseEntity<>("OTP is valid.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OTP.", HttpStatus.BAD_REQUEST);
        }
    }

    //   for reset password
    @Override
    public ResponseEntity<String> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        // Retrieve user by email
        SignUser user = userRepository.findByEmail(resetPasswordRequest.getEmail());

        // Validate user existence
        if (user == null) {
            return new ResponseEntity<>("User with the provided email does not exist.", HttpStatus.NOT_FOUND);
        }

        // Validate password and confirmation match
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmPassword())) {
            return new ResponseEntity<>("New password and confirmation password do not match.", HttpStatus.BAD_REQUEST);
        }

        // Set the new password securely
        user.setPassword(bCryptPasswordEncoder.encode(resetPasswordRequest.getNewPassword()));

        // Clear the forget password OTP since it's no longer needed
        user.setOtp(null);

        userRepository.save(user); // Save the updated user entity

        return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
    }

    // for change password
    @Override
    public ResponseEntity<String> changePassword(ChangePasswordRequest changePasswordRequest) {
        // Retrieve the currently authenticated user
        SignUser currentUser = (SignUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Validate old password
        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), currentUser.getPassword())) {
            return new ResponseEntity<>("Old password is incorrect.", HttpStatus.BAD_REQUEST);
        }

        // Validate that the new password is different from the old password
        if (bCryptPasswordEncoder.matches(changePasswordRequest.getNewPassword(), currentUser.getPassword())) {
            return new ResponseEntity<>("New password must be different from the old password.", HttpStatus.BAD_REQUEST);
        }

        // Set the new password securely
        currentUser.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));

        userRepository.save(currentUser); // Save the updated user entity

        return new ResponseEntity<>("Password changed successfully.", HttpStatus.OK);
    }


}
